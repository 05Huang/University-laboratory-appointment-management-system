package com.mafei.laboratory.system.repository;

import com.mafei.laboratory.system.entity.SysMenu;
import com.mafei.laboratory.system.entity.SysUser;
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
public interface SysMenuRepository extends JpaRepository<SysMenu, Long>, JpaSpecificationExecutor<SysMenu> {

    /**
     * 删除
     *
     * @param menuId
     * @return
     */
    @Modifying
    Integer deleteByMenuId(Long menuId);

    /**
     * 单个查询
     *
     * @param menuId
     * @return
     */
    SysMenu findByMenuId(Long menuId);


    /**
     * 查询菜单
     *
     * @param menuIdS
     * @return
     */
    @Query(value = "select * from sys_menu where menu_id in ?1", nativeQuery = true)
    List<SysMenu> findMenu(List<Long> menuIdS);


    /**
     * 查询多个
     *
     * @param ids
     * @return
     */
    @Query(value = "SELECT * FROM sys_menu WHERE menu_id in ?1", nativeQuery = true)
    List<SysMenu> queryByIds(Set<Long> ids);

    /**
     * 批量删除
     *
     * @param ids
     */
    @Modifying
    @Query(value = "delete from sys_menu where menu_id in ?1", nativeQuery = true)
    void deleteByIds(Set<Long> ids);
}