package com.mafei.laboratory.system.repository;

import com.mafei.laboratory.system.entity.SysUser;
import com.mafei.laboratory.system.entity.vo.LoginUserVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author wts
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser>, CrudRepository<SysUser, Long> {

    /**
     * 通过id查找用户
     *
     * @param id
     * @return user
     */
    SysUser findByUserId(Long id);


    /**
     * 通过账号查找
     *
     * @param loginName
     * @return
     */
    SysUser findByLoginName(String loginName);

    /**
     * 通过用户名查找
     *
     * @param username
     * @return
     */
    SysUser findByUserName(String username);

    /**
     * 根据邮箱查询
     *
     * @param email 邮箱
     * @return /
     */
    SysUser findByEmail(String email);

    /**
     * 根据手机号查询
     *
     * @param phone 手机号
     * @return /
     */
    SysUser findByPhonenumber(String phone);

    /**
     * 根据id删除
     *
     * @param userId
     * @return
     */
    Integer deleteByUserId(Long userId);


    /**
     * 修改密码
     *
     * @param username              用户名
     * @param pass                  密码
     * @param lastPasswordResetTime /
     */
    @Modifying
    @Query(value = "update sys_user set password = ?2 , pwd_reset_time = ?3 where username = ?1", nativeQuery = true)
    void updatePass(String username, String pass, Date lastPasswordResetTime);


    /**
     * 修改邮箱
     *
     * @param username 用户名
     * @param email    邮箱
     */
    @Modifying
    @Query(value = "update sys_user set email = ?2 where username = ?1", nativeQuery = true)
    void updateEmail(String username, String email);

    /**
     * 根据角色查询用户
     *
     * @param roleId /
     * @return /
     */
    @Query(value = "SELECT u.* FROM sys_user u, sys_users_roles r WHERE" +
            " u.user_id = r.user_id AND r.role_id = ?1", nativeQuery = true)
    List<SysUser> findByRoleId(Long roleId);

    /**
     * 根据角色中的部门查询
     *
     * @param deptId /
     * @return /
     */
    @Query(value = "SELECT u.* FROM sys_user u, sys_users_roles r, sys_roles_depts d WHERE " +
            "u.user_id = r.user_id AND r.role_id = d.role_id AND d.dept_id = ?1 group by u.user_id", nativeQuery = true)
    List<SysUser> findByRoleDeptId(Long deptId);

    /**
     * 根据菜单查询
     *
     * @param id 菜单ID
     * @return /
     */
    @Query(value = "SELECT u.* FROM sys_user u, sys_users_roles ur, sys_roles_menus rm WHERE\n" +
            "u.user_id = ur.user_id AND ur.role_id = rm.role_id AND rm.menu_id = ?1 group by u.user_id", nativeQuery = true)
    List<SysUser> findByMenuId(Long id);


    /**
     * 根据岗位查询
     *
     * @param ids /
     * @return /
     */
    @Query(value = "SELECT count(1) FROM sys_user u, sys_users_jobs j WHERE u.user_id = j.user_id AND j.job_id IN ?1", nativeQuery = true)
    int countByJobs(Set<Long> ids);

    /**
     * 根据部门查询
     *
     * @param deptIds /
     * @return /
     */
    @Query(value = "SELECT count(1) FROM sys_user u WHERE u.dept_id IN ?1", nativeQuery = true)
    int countByDepts(Set<Long> deptIds);

    /**
     * 根据id查询多个
     *
     * @param ids /
     * @return /
     */
    @Query(value = "SELECT * FROM sys_user WHERE " +
            "user_id in ?1", nativeQuery = true)
    List<SysUser> queryByIds(Set<Long> ids);

    /**
     * 查询角色名
     *
     * @param userId
     * @return
     */
    @Query(value = "SELECT r.role_id FROM " +
            "sys_user u,sys_user_role sr,sys_role r " +
            "WHERE u.user_id = ?1 AND u.user_id = sr.user_id AND r.role_id = sr.role_id", nativeQuery = true)
    Long queryRoleId(Long userId);

    /**
     * 批量删除
     *
     * @param ids
     */
    @Modifying
    @Query(value = "delete from sys_user where user_id in ?1", nativeQuery = true)
    void deleteByUserIds(Set<Long> ids);
}