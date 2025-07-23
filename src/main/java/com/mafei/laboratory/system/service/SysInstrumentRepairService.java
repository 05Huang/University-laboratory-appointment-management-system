package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.entity.SysInstrument;
import com.mafei.laboratory.system.entity.SysInstrumentRepair;
import com.mafei.laboratory.system.entity.vo.RepairVo;
import com.mafei.laboratory.system.entity.vo.RepairVo;

import java.util.List;
import java.util.Set;

/**
 * 仪器用途表(SysInstrumentRepair)表服务接口
 *
 * @author makejava
 * @since 2021-03-10 08:48:36
 */
public interface SysInstrumentRepairService {
    /**
     * 查询全部
     *
     * @return
     */
    List<RepairVo> findAll();

    /**
     * 根据 状态 查询
     * @param ids
     * @return
     */
    List<SysInstrumentRepair> findAllByStatus(Set<Long> ids);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysInstrumentRepair queryById(Long id);

    /**
     * 新增数据
     *
     * @param repairVo 实例对象
     * @return 实例对象
     */
    void insert(RepairVo repairVo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    void deleteById(Long id);


    /**
     * 批量删除
     *
     * @param ids 主键
     * @return 是否成功
     */
    void deleteByIds(Set<Long> ids);

    /**
     * 修改数据
     *
     * @param repairVo 实例对象
     * @return 实例对象
     */
    void update(RepairVo repairVo);

    /**
     * 批量更新状态
     *
     * @param status
     * @param ids
     */
    void updateStatus(String status, Set<Long> ids);

}
