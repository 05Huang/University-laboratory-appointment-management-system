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

    @Override
    public List<UseVo> findAll() {
        List<SysInstrumentUse> all = useRepository.findAll();
        List<UseVo> list = new ArrayList<>(all.size());
        for (SysInstrumentUse use : all) {
            UseVo useVo = new UseVo();
            BeanUtils.copyProperties(use, useVo);
            String name = useRepository.getName(use.getId());
            useVo.setInstrumentName(name);

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
    public void deleteById(Long id) {
        useRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(Set<Long> ids) {
        try {
            useRepository.deleteByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(UseVo useVo) {
        try {
            SysInstrumentUse instrumentUse = useRepository.findById(useVo.getId()).get();
            instrumentUse.setInstrumentId(useVo.getInstrumentId());
            instrumentUse.setUseDesc(useVo.getUseDesc());
            instrumentUse.setUseTitle(useVo.getUseTitle());
            instrumentUse.setType(useVo.getType());
            instrumentUse.setUpdateBy("admin");
            instrumentUse.setUpdateTime(DateUtils.getDate());

            useRepository.save(instrumentUse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
