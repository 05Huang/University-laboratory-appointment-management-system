package com.mafei.laboratory.system.repository;

import com.mafei.laboratory.system.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author wts
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, Long>, JpaSpecificationExecutor<SysRole> {

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Modifying
    Integer deleteByRoleId(Long id);

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    SysRole findByRoleId(Long id);


    /**
     * 查询菜单
     *
     * @param ids
     * @return
     */
    @Query(value = "select * from sys_role where role_id in ?1", nativeQuery = true)
    List<SysRole> findRole(List<Long> ids);


    /**
     * 查询多个
     *
     * @param ids
     * @return
     */
    @Query(value = "SELECT * FROM sys_role WHERE role_id in ?1", nativeQuery = true)
    List<SysRole> queryByIds(Set<Long> ids);

    /**
     * 批量删除
     *
     * @param ids
     */
    @Modifying
    @Query(value = "delete from sys_role where role_id in ?1", nativeQuery = true)
    void deleteByIds(Set<Long> ids);
}