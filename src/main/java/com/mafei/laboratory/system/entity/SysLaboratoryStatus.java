package com.mafei.laboratory.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实验室状态表
 * @author wts
 */
@Entity
@Table(name = "sys_laboratory_status")
@Data
public class SysLaboratoryStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实验室状态ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 实验室ID
     */
    @Column(name = "laboratory_id", nullable = false)
    private Long laboratoryId;

    /**
     * 0空闲，1未归还
     */
    @Column(name = "status", nullable = false)
    private Integer status;

    /**
     * 10 未损坏，11损坏
     */
    @Column(name = "isDamage")
    private Integer isDamage;

    /**
     * 使用次数
     */
    @Column(name = "useCount")
    private Integer useCount;

    /**
     * 预计恢复时间
     */
    @Column(name = "fix_time")
    private Date fixTime;

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
     * 原因
     */
    @Column(name = "remark")
    private String remark;

}
