package com.mafei.laboratory.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 部门表
 */
@Entity
@Table(name = "sys_dept")
@Data
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id", nullable = false)
    private Long deptId;

    /**
     * 父部门id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 祖级列表
     */
    @Column(name = "ancestors")
    private String ancestors;

    /**
     * 部门名称
     */
    @Column(name = "dept_name")
    private String deptName;

    /**
     * 显示顺序
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 负责人
     */
    @Column(name = "leader")
    private String leader;

    /**
     * 联系电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 部门状态（0正常 1停用）
     */
    @Column(name = "status")
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

}
