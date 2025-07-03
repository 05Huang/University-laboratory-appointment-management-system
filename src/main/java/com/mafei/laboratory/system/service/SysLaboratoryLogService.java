package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.entity.SysLaboratoryLog;

import java.util.List;

/**
 * 角色和菜单关联表(SysLaboratoryLog)表服务接口
 *
 * @author wts
 * @since 2021-02-24 10:39:19
 */
public interface SysLaboratoryLogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysLaboratoryLog queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysLaboratoryLog> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysLaboratoryLog 实例对象
     * @return 实例对象
     */
    SysLaboratoryLog insert(SysLaboratoryLog sysLaboratoryLog);

    /**
     * 修改数据
     *
     * @param sysLaboratoryLog 实例对象
     * @return 实例对象
     */
    SysLaboratoryLog update(SysLaboratoryLog sysLaboratoryLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
