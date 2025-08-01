package com.mafei.laboratory.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 实验室信息表
 */
@Entity
@Table(name = "sys_laboratory")
@Data
public class SysLaboratory {

    /**
     * 实验室ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "laboratory_name", nullable = false)
    private String laboratoryName;

    /**
     * 所属部门
     */
    @Column(name = "depot_id")
    private Long depotId;

    /**
     * 显示顺序
     */
    @Column(name = "order_num")
    private Integer orderNum;

    /**
     * 教室最大容纳人数
     */
    @Column(name = "capacity")
    private Integer capacity;

    /**
     * 所属实验类型
     */
    @Column(name = "target")
    private String target;

    /**
     * 用途类型（0理论教室 1综合教室 2实验教室）
     */
    @Column(name = "use_type")
    private String useType;

    /**
     * 状态（0正常 1停用）
     */
    @Column(name = "status")
    private String status= "0";

    /**
     * 照片
     */
    @Column(name = "image")
    private String image;

    /**
     * 权限标识
     */
    @Column(name = "perms")
    private String perms;

    /**
     * 负责人
     */
    @Column(name = "admin")
    private String admin;

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
