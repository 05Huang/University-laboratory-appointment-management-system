package com.mafei.laboratory.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 仪器用途表
 */
@Table(name = "sys_instrument_repair")
@Entity
@Data
public class SysInstrumentRepair implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仪器id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "instrument_id", nullable = false)
    private Long instrumentId;

    /**
     * （0代表存在 1代表启用）
     */
    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "repair_position")
    private String repairPosition;

    /**
     * 备注
     */
    @Column(name = "comment")
    private String comment;

    /**
     * 创建者
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 维修时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 预计修复时间
     */
    @Column(name = "update_time")
    private Date updateTime;



}
