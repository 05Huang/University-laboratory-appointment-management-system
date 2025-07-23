package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.entity.SysRole;
import com.mafei.laboratory.system.entity.vo.RoleVo;
import com.mafei.laboratory.system.service.dto.AllMenuDto;

import java.util.List;
import java.util.Set;

/**
 * 角色信息表(SysRole)表服务接口
 *
 * @author wutangsheng
 * @since 2021-02-18 11:07:59
 */
public interface SysRoleService {


    /**
     * 查询全部
     *
     * @return
     */
    List<RoleVo> queryAll();

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    RoleVo queryById(Long roleId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysRole> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param roleVo 实例对象
     * @return 实例对象
     */
    Integer insert(RoleVo roleVo);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    Integer deleteById(Long roleId);


    /**
     * 批量删除
     *
     * @param userId 主键
     * @return 是否成功
     */
    void deleteByIds(Set<Long> userId);

    /**
     * 修改数据
     *
     * @param roleVo 实例对象
     * @return 实例对象
     */
    Integer update(RoleVo roleVo);

    /**
     * 批量更新状态
     * @param status
     * @param ids
     */
    void updateStatus(String status, Set<Long> ids);
}
