package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.entity.SysInstrumentUse;
import com.mafei.laboratory.system.entity.vo.UseVo;

import java.util.List;
import java.util.Set;

/**
 * 仪器用途表(SysInstrumentUse)表服务接口
 *
 * @author makejava
 * @since 2021-03-10 08:48:40
 */
public interface SysInstrumentUseService {
    /**
     * 查询全部
     *
     * @return
     */
    List<UseVo> findAll();

    /**
     * 根据 状态 查询
     *
     * @param ids
     * @return
     */
    List<SysInstrumentUse> findAllByStatus(Set<Long> ids);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysInstrumentUse queryById(Long id);

    /**
     * 新增数据
     *
     * @param useVo 实例对象
     * @return 实例对象
     */
    void insert(UseVo useVo);

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
     * @param useVo 实例对象
     * @return 实例对象
     */
    void update(UseVo useVo);

}
