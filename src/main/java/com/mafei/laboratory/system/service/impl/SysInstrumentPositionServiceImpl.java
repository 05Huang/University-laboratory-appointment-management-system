package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.commons.utils.DateUtils;
import com.mafei.laboratory.system.entity.SysInstrument;
import com.mafei.laboratory.system.entity.SysInstrumentPosition;
import com.mafei.laboratory.system.entity.SysInstrumentRepair;
import com.mafei.laboratory.system.entity.vo.InstrumentVo;
import com.mafei.laboratory.system.entity.vo.PositionVo;
import com.mafei.laboratory.system.entity.vo.RepairVo;
import com.mafei.laboratory.system.repository.SysInstrumentPositionRepository;
import com.mafei.laboratory.system.service.SysInstrumentPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 仪器位置表(SysInstrumentPosition)表服务实现类
 *
 * @author makejava
 * @since 2021-03-10 08:48:34
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysInstrumentPositionServiceImpl implements SysInstrumentPositionService {
    private final SysInstrumentPositionRepository positionRepository;

    @Override
    public List<PositionVo> findAll() {
        List<SysInstrumentPosition> all = positionRepository.findAll();
        List<PositionVo> list = new ArrayList<>(all.size());
        for (SysInstrumentPosition use : all) {
            PositionVo positionVo = new PositionVo();
            BeanUtils.copyProperties(use, positionVo);
            String name = positionRepository.getName(use.getId());
            positionVo.setInstrumentName(name);
            list.add(positionVo);
        }
        return list;
    }

    @Override
    public List<SysInstrumentPosition> findAllByStatus(Set<Long> ids) {
        return positionRepository.queryByIds(ids);
    }

    @Override
    public SysInstrumentPosition queryById(Long id) {
        return positionRepository.findById(id).get();
    }

    @Override
    public void insert(PositionVo positionVo) {
        try {
            SysInstrumentPosition position = new SysInstrumentPosition();
            position.setId(positionVo.getId());
            position.setInstrumentId(positionVo.getInstrumentId());
            position.setPosition(positionVo.getPosition());
            position.setDetailNumber(positionVo.getDetailNumber());
            position.setRepairPosition(positionVo.getRepairPosition());
            position.setComment(positionVo.getComment());
            position.setCreateBy("admin");
            position.setCreateTime(DateUtils.getDate());
            positionRepository.save(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        positionRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(Set<Long> ids) {
        try {
            positionRepository.deleteByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(PositionVo positionVo) {
        try {
            SysInstrumentPosition position = positionRepository.findById(positionVo.getId()).get();
            position.setId(positionVo.getId());
            position.setPosition(positionVo.getPosition());
            position.setDetailNumber(positionVo.getDetailNumber());
            position.setComment(positionVo.getComment());
            position.setCreateBy("admin");
            position.setUpdateTime(DateUtils.getDate());
            positionRepository.save(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
