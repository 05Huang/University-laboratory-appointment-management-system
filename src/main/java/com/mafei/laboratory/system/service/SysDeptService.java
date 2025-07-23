package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.entity.SysDept;

import java.util.List;

/**
 * 部门表(SysDept)表服务接口
 *
 * @author makejava
 * @since 2021-03-10 08:48:29
 */
public interface SysDeptService {
    List<SysDept> findAll();
}
