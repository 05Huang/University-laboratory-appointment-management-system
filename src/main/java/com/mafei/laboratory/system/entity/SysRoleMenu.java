package com.mafei.laboratory.system.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色和菜单关联表
 * @author wts
 */
@Entity
@Data
@Table(name = "sys_role_menu")
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Id
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    /**
     * 菜单ID
     */
    @Column(name = "menu_id", nullable = false)
    private Long menuId;

}
