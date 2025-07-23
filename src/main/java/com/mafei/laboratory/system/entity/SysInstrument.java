package com.mafei.laboratory.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 仪器信息表
 */
@Entity
@Table(name = "sys_instrument")
@Data
public class SysInstrument implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仪器id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "depot_id")
    private Long depotId;

    /**
     * 名字
     */
    @Column(name = "instrument_name")
    private String instrumentName;

    /**
     * 型号
     */
    @Column(name = "model")
    private String model;

    /**
     * 类型（0常规设备 1自制设备 2捐赠设备）
     */
    @Column(name = "type")
    private String type;

    /**
     * （0代表存在 1代表启用）
     */
    @Column(name = "status")
    private String status;

    /**
     * 仪器价格
     */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /**
     * 创建者
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 购入时间
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
