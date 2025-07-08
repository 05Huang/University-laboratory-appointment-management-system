package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.commons.enums.StatusEnum;
import com.mafei.laboratory.commons.exception.BadRequestException;
import com.mafei.laboratory.system.entity.SysBorrowLaboratory;
import com.mafei.laboratory.system.entity.SysLaboratory;
import com.mafei.laboratory.system.entity.SysUser;
import com.mafei.laboratory.system.entity.vo.BorrowLaboratoryVo;
import com.mafei.laboratory.system.repository.SysBorrowLaboratoryRepository;
import com.mafei.laboratory.system.repository.SysLaboratoryRepository;
import com.mafei.laboratory.system.repository.SysUserRepository;
import com.mafei.laboratory.system.service.SysBorrowLaboratoryService;
import com.mafei.laboratory.system.service.dto.UpdateDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * 设备借用表(SysBorrowLaboratory)表服务实现类
 *
 * @author wts
 * @since 2021-03-07 08:48:26
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysBorrowLaboratoryServiceImpl implements SysBorrowLaboratoryService {
    private final SysBorrowLaboratoryRepository repository;
    private final SysLaboratoryRepository laboratoryRepository;
    private final SysUserRepository userRepository;

    private List<BorrowLaboratoryVo> list;

    @Override
    public List<BorrowLaboratoryVo> findAll() {
        return repository.myFindAll();
    }

    @Override
    public List<BorrowLaboratoryVo> findAllByUserId(Long userId) {
        List<SysBorrowLaboratory> all = repository.findByUserId(userId);
        list = new LinkedList<>();
        for (SysBorrowLaboratory borrowInstrument : all) {
            BorrowLaboratoryVo vo = new BorrowLaboratoryVo();
            BeanUtils.copyProperties(borrowInstrument, vo);
            vo.setLaboratoryName(laboratoryRepository.getName(vo.getLaboratoryId()));
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<SysBorrowLaboratory> findAllByStatus(Set<Long> ids) {
        return null;
    }

    @Override
    public SysBorrowLaboratory queryById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void insert(BorrowLaboratoryVo borrowVo) {
        SysBorrowLaboratory borrowInstrument = new SysBorrowLaboratory();
        borrowInstrument.setUserId(borrowVo.getUserId());
        borrowInstrument.setLaboratoryId(borrowVo.getLaboratoryId());
        borrowInstrument.setStatus(borrowVo.getStatus());
        borrowInstrument.setBorrowStatus(StatusEnum.CHECK.getStatus());
        borrowInstrument.setComment(borrowVo.getComment());
        borrowInstrument.setCreateBy(borrowVo.getUserName());
        borrowInstrument.setCreateTime(borrowVo.getCreateTime());
        borrowInstrument.setUpdateTime(new Date());

        repository.save(borrowInstrument);
    }

    @Override
    public void updateBorrow(String status, Long id) {
        try {
            repository.updateBorrowStatus(id, status);
            Long instrumentId = queryById(id).getLaboratoryId();
            StatusEnum statusEnum = checkStatus(status);
            laboratoryRepository.updateStatus(instrumentId, statusEnum.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBorrow(String status, Set<Long> ids) {
        try {
            List<SysBorrowLaboratory> list = repository.queryByIds(ids);
            HashSet<Long> set = new HashSet<>(list.size());
            for (SysBorrowLaboratory laboratory : list) {
                laboratory.setBorrowStatus(status);
                set.add(laboratory.getLaboratoryId());
            }
            List<SysLaboratory> laboratories = laboratoryRepository.queryByIds(set);
            for (SysLaboratory laboratory : laboratories) {
                laboratory.setStatus(StatusEnum.NORMAL.getStatus());
            }
            repository.saveAll(list);
            laboratoryRepository.saveAll(laboratories);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCheck(UpdateDto updateDto) {
        try {
            SysBorrowLaboratory sysBorrowLaboratory = queryById(updateDto.getId());
            Long userId = sysBorrowLaboratory.getUserId();
            SysUser user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new BadRequestException(HttpStatus.FORBIDDEN, "异常错误");
            }

            StatusEnum statusEnum = checkStatus(updateDto.getStatus());

            String status = updateDto.getStatus().equals("8") ? updateDto.getStatus() : statusEnum.getStatus();
            sysBorrowLaboratory.setStatus(status);

            sysBorrowLaboratory.setBorrowStatus(updateDto.getStatus());

            String comment = StringUtils.isEmpty(updateDto.getComment()) ? sysBorrowLaboratory.getComment() : updateDto.getComment();
            sysBorrowLaboratory.setComment(comment);

            repository.save(sysBorrowLaboratory);

            Long laboratoryId = sysBorrowLaboratory.getLaboratoryId();
            laboratoryRepository.updateStatus(laboratoryId, statusEnum.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCheck(String status, Set<Long> ids) {
        List<SysBorrowLaboratory> list = repository.queryByIds(ids);
        HashSet<Long> set = new HashSet<>(list.size());
        for (SysBorrowLaboratory instrument : list) {
            instrument.setBorrowStatus(status);
            set.add(instrument.getLaboratoryId());
        }
        List<SysLaboratory> laboratories = laboratoryRepository.queryByIds(set);

        StatusEnum statusEnum = checkStatus(status);

        for (SysLaboratory laboratory : laboratories) {
            laboratory.setStatus(statusEnum.getStatus());
        }
        repository.saveAll(list);
        laboratoryRepository.saveAll(laboratories);
    }

    @Override
    public Long countAll() {
        return repository.count();
    }

    @Override
    public Long countAllByStatus(String status) {
        return repository.countByBorrowStatus(status);
    }

    private StatusEnum checkStatus(String status) {
        switch (status) {
            case "3":
            case "4":
            case "8":
                return StatusEnum.NORMAL;
            case "5":
                return StatusEnum.CHECK;
            case "7":
                return StatusEnum.BORROW;
            default:
                return StatusEnum.DEPRECATED;
        }
    }
}
