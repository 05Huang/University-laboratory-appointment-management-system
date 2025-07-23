package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.commons.enums.StatusEnum;
import com.mafei.laboratory.commons.utils.DateUtils;
import com.mafei.laboratory.system.entity.SysInstrumentRepair;
import com.mafei.laboratory.system.entity.vo.RepairVo;
import com.mafei.laboratory.system.repository.SysInstrumentRepairRepository;
import com.mafei.laboratory.system.repository.SysInstrumentRepository;
import com.mafei.laboratory.system.service.SysInstrumentRepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 仪器用途表(SysInstrumentRepair)表服务实现类
 *
 * @author makejava
 * @since 2021-03-10 08:48:37
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysInstrumentRepairServiceImpl implements SysInstrumentRepairService {
    private final SysInstrumentRepairRepository repairRepository;
    private final SysInstrumentRepository instrumentRepository;

    @Override
    public List<RepairVo> findAll() {
        List<SysInstrumentRepair> all = repairRepository.findAll();
        List<RepairVo> list = new ArrayList<>(all.size());
        for (SysInstrumentRepair use : all) {
            RepairVo repairVo = new RepairVo();
            BeanUtils.copyProperties(use, repairVo);
            String name = repairRepository.getName(use.getId());
            repairVo.setInstrumentName(name);

            list.add(repairVo);
        }
        return list;
    }

    @Override
    public List<SysInstrumentRepair> findAllByStatus(Set<Long> ids) {
        return repairRepository.queryByIds(ids);
    }

    @Override
    public SysInstrumentRepair queryById(Long id) {
        return repairRepository.findById(id).get();
    }

    @Override
    public void insert(RepairVo repairVo) {
        try {
            SysInstrumentRepair instrumentRepair = new SysInstrumentRepair();
            instrumentRepair.setId(repairVo.getId());
            instrumentRepair.setInstrumentId(repairVo.getInstrumentId());
            instrumentRepair.setPrice(repairVo.getPrice());
            instrumentRepair.setRepairPosition(repairVo.getRepairPosition());
            instrumentRepair.setComment(repairVo.getComment());
            instrumentRepair.setCreateBy("admin");
            instrumentRepair.setCreateTime(repairVo.getCreateTime());
            instrumentRepair.setUpdateTime(repairVo.getUpdateTime());
            repairRepository.save(instrumentRepair);
            instrumentRepository.updateStatus(repairVo.getInstrumentId(), StatusEnum.REPAIR.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            repairRepository.deleteById(id);
            SysInstrumentRepair repair = repairRepository.findById(id).get();
            instrumentRepository.updateStatus(repair.getInstrumentId(), StatusEnum.NORMAL.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteByIds(Set<Long> ids) {
        try {
            repairRepository.deleteByIds(ids);
            updateStatus(StatusEnum.NORMAL.getStatus(), ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(RepairVo repairVo) {
        try {
            SysInstrumentRepair instrumentRepair = repairRepository.findById(repairVo.getId()).get();
            instrumentRepair.setInstrumentId(repairVo.getInstrumentId());
            instrumentRepair.setPrice(repairVo.getPrice());
            instrumentRepair.setRepairPosition(repairVo.getRepairPosition());
            instrumentRepair.setComment(repairVo.getComment());
            instrumentRepair.setUpdateBy("admin");
            instrumentRepair.setUpdateTime(DateUtils.getDate());
            repairRepository.save(instrumentRepair);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(String status, Set<Long> ids) {
        try {
            List<SysInstrumentRepair> list = repairRepository.queryByIds(ids);
            for (SysInstrumentRepair repair : list) {
                instrumentRepository.updateStatus(repair.getInstrumentId(), status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
