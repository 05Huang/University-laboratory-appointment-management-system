package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.commons.utils.DateUtils;
import com.mafei.laboratory.system.entity.SysInstrument;
import com.mafei.laboratory.system.entity.SysInstrumentUse;
import com.mafei.laboratory.system.entity.vo.InstrumentVo;
import com.mafei.laboratory.system.entity.vo.UseVo;
import com.mafei.laboratory.system.repository.SysInstrumentRepository;
import com.mafei.laboratory.system.repository.SysInstrumentUseRepository;
import com.mafei.laboratory.system.service.SysInstrumentUseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 仪器用途表(SysInstrumentUse)表服务实现类
 *
 * @author wts
 * @since 2021-03-10 08:48:41
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysInstrumentUseServiceImpl implements SysInstrumentUseService {
    private final SysInstrumentUseRepository useRepository;
    private final SysInstrumentRepository instrumentRepository;

    @Override
    public List<UseVo> findAll() {
        List<SysInstrumentUse> all = useRepository.findAll();
        List<UseVo> list = new ArrayList<>(all.size());
        for (SysInstrumentUse use : all) {
            UseVo useVo = new UseVo();
            BeanUtils.copyProperties(use, useVo);
            String name = useRepository.getName(use.getId());
            String image= instrumentRepository.getImage(use.getInstrumentId());
            useVo.setInstrumentName(name);
            useVo.setImage(image);
            list.add(useVo);
        }
        return list;
    }

    @Override
    public List<SysInstrumentUse> findAllByStatus(Set<Long> ids) {
        return useRepository.queryByIds(ids);
    }

    @Override
    public SysInstrumentUse queryById(Long id) {
        return useRepository.findById(id).get();
    }

    @Override
    public void insert(UseVo useVo) {
        try {
            SysInstrumentUse instrumentUse = new SysInstrumentUse();
            instrumentUse.setId(useVo.getId());
            instrumentUse.setInstrumentId(useVo.getInstrumentId());
            instrumentUse.setUseDesc(useVo.getUseDesc());
            instrumentUse.setUseTitle(useVo.getUseTitle());
            instrumentUse.setType(useVo.getType());
            instrumentUse.setCreateBy("admin");
            instrumentUse.setCreateTime(DateUtils.getDate());

            useRepository.save(instrumentUse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("设备用途 ID 不能为空");
        }
        
        try {
            useRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("删除设备用途失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("设备用途 ID 列表不能为空");
        }
        
        if (ids.contains(null)) {
            throw new IllegalArgumentException("设备用途 ID 列表包含无效ID");
        }
        
        try {
            useRepository.deleteByIds(ids);
        } catch (Exception e) {
            throw new RuntimeException("批量删除设备用途失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void update(UseVo useVo) {
        // 验证 ID 字段
        if (useVo.getId() == null) {
            throw new IllegalArgumentException("设备用途 ID 不能为空");
        }
        
        try {
            // 检查记录是否存在
            SysInstrumentUse instrumentUse = useRepository.findById(useVo.getId())
                    .orElseThrow(() -> new RuntimeException("设备用途记录不存在"));
            
            // 更新字段
            instrumentUse.setInstrumentId(useVo.getInstrumentId());
            instrumentUse.setUseDesc(useVo.getUseDesc());
            instrumentUse.setUseTitle(useVo.getUseTitle());
            instrumentUse.setType(useVo.getType());
            instrumentUse.setUpdateBy("admin");
            instrumentUse.setUpdateTime(DateUtils.getDate());

            useRepository.save(instrumentUse);
        } catch (Exception e) {
            throw new RuntimeException("更新设备用途失败: " + e.getMessage());
        }
    }


}
