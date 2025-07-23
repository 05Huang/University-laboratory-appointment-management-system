package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.commons.exception.BadRequestException;
import com.mafei.laboratory.system.entity.SysLaboratory;
import com.mafei.laboratory.system.entity.vo.LaboratoryVo;
import com.mafei.laboratory.system.repository.SysLaboratoryRepository;
import com.mafei.laboratory.system.service.SysLaboratoryService;
import com.mafei.laboratory.system.service.dto.LaboratoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

/**
 * @author wutangsheng
 * @info
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysLaboratoryServiceImpl implements SysLaboratoryService {
    private final SysLaboratoryRepository laboratoryRepository;

    @Override
    public List<LaboratoryDto> findAll() {
        return laboratoryRepository.queryAll();
    }

    @Override
    public List<SysLaboratory> findAllByStatus() {
        return laboratoryRepository.findByStatus();
    }

    @Override
    public SysLaboratory queryById(Long id) {
        return null;
    }

    @Override
    public void insert(LaboratoryVo laboratoryVo) {
        SysLaboratory laboratory = new SysLaboratory();
        laboratory.setLaboratoryName(laboratoryVo.getLaboratoryName());
        laboratory.setTarget(laboratoryVo.getTarget());
        laboratory.setDepotId(laboratoryVo.getDepotId());
        laboratory.setCapacity(laboratoryVo.getCapacity());
        laboratory.setImage(laboratoryVo.getImage());
        laboratory.setAdmin("admin");
        laboratoryRepository.save(laboratory);
    }

    @Override
    public void deleteById(Long id) {
        laboratoryRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(Set<Long> ids) {
        try {
            laboratoryRepository.deleteByIds(ids);
        } catch (BadRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(LaboratoryVo laboratoryVo) {
        try {
            SysLaboratory sysLaboratory = laboratoryRepository.findById(laboratoryVo.getId()).get();
            sysLaboratory.setLaboratoryName(laboratoryVo.getLaboratoryName());
            sysLaboratory.setCapacity(laboratoryVo.getCapacity());
            sysLaboratory.setTarget(laboratoryVo.getTarget());
            laboratoryRepository.save(sysLaboratory);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateStatus(String status, Set<Long> ids) {
        try {
            List<SysLaboratory> list = laboratoryRepository.queryByIds(ids);
            for (SysLaboratory laboratory : list) {
                laboratory.setStatus(status);
            }
            laboratoryRepository.saveAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
