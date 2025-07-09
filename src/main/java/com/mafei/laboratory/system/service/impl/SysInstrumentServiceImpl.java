package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.commons.enums.StatusEnum;
import com.mafei.laboratory.system.entity.SysInstrument;
import com.mafei.laboratory.system.entity.vo.InstrumentVo;
import com.mafei.laboratory.system.repository.SysBorrowInstrumentRepository;
import com.mafei.laboratory.system.repository.SysInstrumentRepairRepository;
import com.mafei.laboratory.system.repository.SysInstrumentRepository;
import com.mafei.laboratory.system.service.SysInstrumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 仪器信息表(SysInstrument)表服务实现类
 *
 * @author wts
 * @since 2021-03-10 08:48:31
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysInstrumentServiceImpl implements SysInstrumentService {
    private final SysInstrumentRepository instrumentRepository;
    private final SysInstrumentRepairRepository repairRepository;
    private final SysBorrowInstrumentRepository borrowRepository;

    @Override
    public List<SysInstrument> findAll() {
        return instrumentRepository.findAll();
    }

    @Override
    public List<SysInstrument> findAllRepair() {
        return instrumentRepository.queryByStatus();
    }

    @Override
    public List<SysInstrument> findAllByStatus(Set<Long> ids) {
        return instrumentRepository.queryByIds(ids);
    }

    @Override
    public SysInstrument queryById(Long id) {
        return instrumentRepository.findById(id).get();
    }

    @Override
    public void insert(InstrumentVo instrumentVo) {
        try {
            SysInstrument sysInstrument = new SysInstrument();
            sysInstrument.setDepotId(instrumentVo.getDepotId());
            sysInstrument.setInstrumentName(instrumentVo.getInstrumentName());
            sysInstrument.setModel(instrumentVo.getModel());
            sysInstrument.setType(instrumentVo.getType());
            sysInstrument.setStatus(StatusEnum.NORMAL.getStatus());
            sysInstrument.setPrice(instrumentVo.getPrice());
            sysInstrument.setCreateBy("admin");
            sysInstrument.setImage(instrumentVo.getImage());
            sysInstrument.setCreateTime(new Date());
            instrumentRepository.save(sysInstrument);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        instrumentRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(Set<Long> ids) {
        try {
            instrumentRepository.deleteByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(InstrumentVo instrumentVo) {
        try {
            SysInstrument sysInstrument = instrumentRepository.findById(instrumentVo.getId()).get();
            sysInstrument.setInstrumentName(instrumentVo.getInstrumentName());
            sysInstrument.setModel(instrumentVo.getModel());
            sysInstrument.setType(instrumentVo.getType());
            sysInstrument.setStatus(instrumentVo.getStatus());
            sysInstrument.setPrice(instrumentVo.getPrice());
            sysInstrument.setCreateBy("admin");
            sysInstrument.setImage(instrumentVo.getImage());
            instrumentRepository.save(sysInstrument);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(String status, Set<Long> ids) {
        try {
            List<SysInstrument> list = instrumentRepository.queryByIds(ids);
            for (SysInstrument instrument : list) {
                instrument.setStatus(status);
            }
            instrumentRepository.saveAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
