package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.entity.SysLaboratoryStatus;

import java.util.List;

/**
 * 实验室状态表(SysLaboratoryStatus)表服务接口
 *
 * @author wutangsheng
 * @since 2021-02-24 10:39:23
 */
public interface SysLaboratoryStatusService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysLaboratoryStatus queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysLaboratoryStatus> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysLaboratoryStatus 实例对象
     * @return 实例对象
     */
    SysLaboratoryStatus insert(SysLaboratoryStatus sysLaboratoryStatus);

    /**
     * 修改数据
     *
     * @param sysLaboratoryStatus 实例对象
     * @return 实例对象
     */
    SysLaboratoryStatus update(SysLaboratoryStatus sysLaboratoryStatus);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
