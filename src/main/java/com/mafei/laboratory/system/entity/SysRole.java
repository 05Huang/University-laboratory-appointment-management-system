package com.mafei.laboratory.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色信息表
 * @author wts
 */
@Table(name = "sys_role")
@Entity
@Data
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    /**
     * 角色名称
     */
    @Column(name = "role_name", nullable = false)
    private String roleName;

    /**
     * 角色权限字符串
     */
    @Column(name = "role_key", nullable = false)
    private String roleKey;

    /**
     * 显示顺序
     */
    @Column(name = "role_sort", nullable = false)
    private Integer roleSort;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @Column(name = "data_scope")
    private String dataScope;

    /**
     * 角色状态（0正常 1停用）
     */
    @Column(name = "status", nullable = false)
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @Column(name = "del_flag")
    private String delFlag;

    /**
     * 创建者
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

}
