package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.system.entity.SysDept;
import com.mafei.laboratory.system.repository.SysDeptRepository;
import com.mafei.laboratory.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门表(SysDept)表服务实现类
 *
 * @author makejava
 * @since 2021-03-10 08:48:29
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl implements SysDeptService {
    private final SysDeptRepository deptRepository;

    @Override
    public List<SysDept> findAll() {
        return deptRepository.findAll();
    }
}
