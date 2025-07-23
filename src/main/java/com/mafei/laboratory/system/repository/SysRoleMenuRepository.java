package com.mafei.laboratory.system.repository;

import com.mafei.laboratory.system.entity.SysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wts
 */
@Repository
public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenu, Long>, JpaSpecificationExecutor<SysRoleMenu> {

    /**
     * 根据角色查询菜单id
     *
     * @param roleId
     * @return
     */
    @Query(value = "select menu_id from sys_role_menu where role_id =?1", nativeQuery = true)
    List<Long> findByRoleId(Long roleId);
}