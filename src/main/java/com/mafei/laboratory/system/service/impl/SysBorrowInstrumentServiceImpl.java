package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.commons.enums.StatusEnum;
import com.mafei.laboratory.commons.exception.BadRequestException;
import com.mafei.laboratory.system.entity.SysBorrowInstrument;
import com.mafei.laboratory.system.entity.SysInstrument;
import com.mafei.laboratory.system.entity.SysUser;
import com.mafei.laboratory.system.entity.vo.BorrowInstrumentVo;
import com.mafei.laboratory.system.repository.SysBorrowInstrumentRepository;
import com.mafei.laboratory.system.repository.SysInstrumentRepository;
import com.mafei.laboratory.system.repository.SysUserRepository;
import com.mafei.laboratory.system.service.SysBorrowInstrumentService;
import com.mafei.laboratory.system.service.dto.UpdateDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

/**
 * 设备借用表(SysBorrowInstrument)表服务实现类
 *
 * @author makejava
 * @since 2021-03-10 08:48:25
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysBorrowInstrumentServiceImpl implements SysBorrowInstrumentService {
    private final SysBorrowInstrumentRepository repository;
    private final SysInstrumentRepository instrumentRepository;
    private final SysUserRepository userRepository;

    private List<BorrowInstrumentVo> list;

    @Override
    public List<BorrowInstrumentVo> findAll() {
        return repository.myFindAll();
    }

    @Override
    public List<BorrowInstrumentVo> findAllByUserId(Long userId) {
        List<SysBorrowInstrument> all = repository.findByUserId(userId);
        list = new LinkedList<>();
        for (SysBorrowInstrument borrowInstrument : all) {
            BorrowInstrumentVo vo = new BorrowInstrumentVo();
            BeanUtils.copyProperties(borrowInstrument, vo);
            vo.setInstrumentName(instrumentRepository.getName(vo.getInstrumentId()));
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<SysBorrowInstrument> findAllByStatus(Set<Long> ids) {
        return null;
    }

    @Override
    public SysBorrowInstrument queryById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void insert(BorrowInstrumentVo borrowVo) {
        SysBorrowInstrument borrowInstrument = new SysBorrowInstrument();
        borrowInstrument.setUserId(borrowVo.getUserId());
        borrowInstrument.setInstrumentId(borrowVo.getInstrumentId());
        borrowInstrument.setStatus(borrowVo.getStatus());
        borrowInstrument.setBorrowStatus(borrowVo.getBorrowStatus());
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
            Long instrumentId = queryById(id).getInstrumentId();
            StatusEnum statusEnum = checkStatus(status);
            instrumentRepository.updateStatus(instrumentId, statusEnum.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBorrow(String status, Set<Long> ids) {
        try {
            List<SysBorrowInstrument> list = repository.queryByIds(ids);
            HashSet<Long> set = new HashSet<>(list.size());
            for (SysBorrowInstrument instrument : list) {
                instrument.setBorrowStatus(status);
                set.add(instrument.getInstrumentId());
            }
            List<SysInstrument> instruments = instrumentRepository.queryByIds(set);
            for (SysInstrument instrument : instruments) {
                instrument.setStatus(StatusEnum.NORMAL.getStatus());
            }
            repository.saveAll(list);
            instrumentRepository.saveAll(instruments);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCheck(UpdateDto updateDto) {
        try {
            SysBorrowInstrument sysBorrowInstrument = queryById(updateDto.getId());
            Long userId = sysBorrowInstrument.getUserId();
            SysUser user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new BadRequestException(HttpStatus.FORBIDDEN, "异常错误");
            }
            StatusEnum statusEnum = checkStatus(updateDto.getStatus());

            String status = "";
            if (updateDto.getStatus().equals("8")) {
                status = updateDto.getStatus();
            } else if (updateDto.getStatus().equals("4")) {
                status = updateDto.getStatus();
            } else {
                status = statusEnum.getStatus();
            }

            sysBorrowInstrument.setStatus(status);

            sysBorrowInstrument.setBorrowStatus(updateDto.getStatus());

            String comment = StringUtils.isEmpty(updateDto.getComment()) ? sysBorrowInstrument.getComment() : updateDto.getComment();
            sysBorrowInstrument.setComment(comment);

            repository.save(sysBorrowInstrument);

            Long instrumentId = sysBorrowInstrument.getInstrumentId();
            instrumentRepository.updateStatus(instrumentId, statusEnum.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCheck(String status, Set<Long> ids) {
        List<SysBorrowInstrument> list = repository.queryByIds(ids);
        HashSet<Long> set = new HashSet<>(list.size());
        StatusEnum statusEnum = checkStatus(status);

        for (SysBorrowInstrument instrument : list) {
            instrument.setBorrowStatus(status);
            instrument.setStatus(statusEnum.getStatus());
            set.add(instrument.getInstrumentId());
        }
        List<SysInstrument> instruments = instrumentRepository.queryByIds(set);

        for (SysInstrument instrument : instruments) {
            instrument.setStatus(statusEnum.getStatus());
        }
        repository.saveAll(list);
        instrumentRepository.saveAll(instruments);
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
