package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.entity.SysInstrument;
import com.mafei.laboratory.system.entity.SysLaboratory;
import com.mafei.laboratory.system.entity.vo.InstrumentVo;
import com.mafei.laboratory.system.entity.vo.LaboratoryVo;
import com.mafei.laboratory.system.service.dto.LaboratoryDto;

import java.util.List;
import java.util.Set;

/**
 * 仪器信息表(SysInstrument)表服务接口
 *
 * @author wts
 * @since 2021-03-10 08:48:30
 */
public interface SysInstrumentService {
    /**
     * 查询全部
     *
     * @return
     */
    List<SysInstrument> findAll();

    /**
     * 查询全部
     *
     * @return
     */
    List<SysInstrument> findAllRepair();

    /**
     * 根据 状态 查询
     * @param ids
     * @return
     */
    List<SysInstrument> findAllByStatus(Set<Long> ids);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysInstrument queryById(Long id);

    /**
     * 新增数据
     *
     * @param instrumentVo 实例对象
     * @return 实例对象
     */
    void insert(InstrumentVo instrumentVo);

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
     * @param instrumentVo 实例对象
     * @return 实例对象
     */
    void update(InstrumentVo instrumentVo);

    /**
     * 批量更新状态
     *
     * @param status
     * @param ids
     */
    void updateStatus(String status, Set<Long> ids);
}
