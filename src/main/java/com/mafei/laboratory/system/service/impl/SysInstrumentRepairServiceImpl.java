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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 仪器用途表(SysInstrumentRepair)表服务实现类
 *
 * @author makejava
 * @since 2021-03-10 08:48:37
 */
@Service
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
            String image = instrumentRepository.getImage(use.getInstrumentId());
            repairVo.setInstrumentName(name);
            repairVo.setImage(image);
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
        Optional<SysInstrumentRepair> repair = repairRepository.findById(id);
        return repair.orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(RepairVo repairVo) {
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
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        Optional<SysInstrumentRepair> optionalRepair = repairRepository.findById(id);
        if (optionalRepair.isPresent()) {
            SysInstrumentRepair repair = optionalRepair.get();
            Long instrumentId = repair.getInstrumentId();
            repairRepository.deleteById(id);
            instrumentRepository.updateStatus(instrumentId, StatusEnum.NORMAL.getStatus());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(Set<Long> ids) {
        List<SysInstrumentRepair> repairs = repairRepository.queryByIds(ids);
        if (!repairs.isEmpty()) {
            repairRepository.deleteByIds(ids);
            updateStatus(StatusEnum.NORMAL.getStatus(), ids);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RepairVo repairVo) {
        Optional<SysInstrumentRepair> optionalRepair = repairRepository.findById(repairVo.getId());
        if (optionalRepair.isPresent()) {
            SysInstrumentRepair instrumentRepair = optionalRepair.get();
            instrumentRepair.setInstrumentId(repairVo.getInstrumentId());
            instrumentRepair.setPrice(repairVo.getPrice());
            instrumentRepair.setRepairPosition(repairVo.getRepairPosition());
            instrumentRepair.setComment(repairVo.getComment());
            instrumentRepair.setUpdateBy("admin");
            instrumentRepair.setUpdateTime(DateUtils.getDate());
            repairRepository.save(instrumentRepair);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String status, Set<Long> ids) {
        List<SysInstrumentRepair> list = repairRepository.queryByIds(ids);
        for (SysInstrumentRepair repair : list) {
            instrumentRepository.updateStatus(repair.getInstrumentId(), status);
        }
    }
}
