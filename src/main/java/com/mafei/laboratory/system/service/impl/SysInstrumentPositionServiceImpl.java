package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.commons.utils.DateUtils;
import com.mafei.laboratory.system.entity.SysInstrument;
import com.mafei.laboratory.system.entity.SysInstrumentPosition;
import com.mafei.laboratory.system.entity.SysInstrumentRepair;
import com.mafei.laboratory.system.entity.vo.InstrumentVo;
import com.mafei.laboratory.system.entity.vo.PositionVo;
import com.mafei.laboratory.system.entity.vo.RepairVo;
import com.mafei.laboratory.system.repository.SysInstrumentPositionRepository;
import com.mafei.laboratory.system.repository.SysInstrumentRepository;
import com.mafei.laboratory.system.service.SysInstrumentPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
@RequiredArgsConstructor
public class SysInstrumentPositionServiceImpl implements SysInstrumentPositionService {
    private final SysInstrumentPositionRepository positionRepository;
    private final SysInstrumentRepository instrumentRepository;

    @Override
    public List<PositionVo> findAll() {
        List<SysInstrumentPosition> all = positionRepository.findAll();
        List<PositionVo> list = new ArrayList<>(all.size());
        for (SysInstrumentPosition use : all) {
            PositionVo positionVo = new PositionVo();
            BeanUtils.copyProperties(use, positionVo);
            String name = positionRepository.getName(use.getId());
            String image = instrumentRepository.getImage(use.getInstrumentId());
            positionVo.setInstrumentName(name);
            positionVo.setImage(image);
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
        return positionRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void insert(PositionVo positionVo) {
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
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        positionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIds(Set<Long> ids) {
        positionRepository.deleteByIds(ids);
    }

    @Override
    @Transactional
    public void update(PositionVo positionVo) {
        // 验证 ID 字段
        if (positionVo.getId() == null) {
            throw new IllegalArgumentException("设备位置 ID 不能为空");
        }
        
        // 检查记录是否存在
        SysInstrumentPosition position = positionRepository.findById(positionVo.getId())
                .orElseThrow(() -> new RuntimeException("设备位置记录不存在"));
        
        // 更新字段
        position.setPosition(positionVo.getPosition());
        position.setDetailNumber(positionVo.getDetailNumber());
        position.setComment(positionVo.getComment());
        position.setUpdateTime(DateUtils.getDate());
        
        // 保存更新
        positionRepository.save(position);
    }
}
