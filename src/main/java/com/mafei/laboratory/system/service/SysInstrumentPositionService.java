package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.entity.SysInstrument;
import com.mafei.laboratory.system.entity.SysInstrumentPosition;
import com.mafei.laboratory.system.entity.vo.PositionVo;

import java.util.List;
import java.util.Set;

/**
 * 仪器位置表(SysInstrumentPosition)表服务接口
 *
 * @author makejava
 * @since 2021-03-10 08:48:33
 */
public interface SysInstrumentPositionService {
    /**
     * 查询全部
     *
     * @return
     */
    List<PositionVo> findAll();

    /**
     * 根据 状态 查询
     *
     * @param ids
     * @return
     */
    List<SysInstrumentPosition> findAllByStatus(Set<Long> ids);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysInstrumentPosition queryById(Long id);

    /**
     * 新增数据
     *
     * @param positionVo 实例对象
     * @return 实例对象
     */
    void insert(PositionVo positionVo);

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
     * @param positionVo 实例对象
     * @return 实例对象
     */
    void update(PositionVo positionVo);

}
