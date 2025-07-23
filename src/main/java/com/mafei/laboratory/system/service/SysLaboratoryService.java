package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.entity.SysLaboratory;
import com.mafei.laboratory.system.entity.SysRole;
import com.mafei.laboratory.system.entity.vo.LaboratoryVo;
import com.mafei.laboratory.system.entity.vo.RoleVo;
import com.mafei.laboratory.system.service.dto.LaboratoryDto;

import java.util.List;
import java.util.Set;

/**
 * 实验室信息表(SysLaboratory)表服务接口
 *
 * @author wutangsheng
 * @since 2021-02-24 10:39:21
 */
public interface SysLaboratoryService {
    /**
     * 查询全部
     * @return
     */
    List<LaboratoryDto> findAll();

    /**
     * 查询全部
     * @return
     */
    List<SysLaboratory> findAllByStatus();


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysLaboratory queryById(Long id);

    /**
     * 新增数据
     *
     * @param laboratoryVo 实例对象
     * @return 实例对象
     */
    void insert(LaboratoryVo laboratoryVo);

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
     * @param laboratoryVo 实例对象
     * @return 实例对象
     */
    void update(LaboratoryVo laboratoryVo);

    /**
     * 批量更新状态
     * @param status
     * @param ids
     */
    void updateStatus(String status, Set<Long> ids);
}
