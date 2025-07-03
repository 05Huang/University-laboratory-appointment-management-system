package com.mafei.laboratory.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 仪器用途表
 * @author wts
 */
@Entity
@Table(name = "sys_instrument_use")
@Data
public class SysInstrumentUse implements Serializable {

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
     * 用途描述
     */
    @Column(name = "use_desc")
    private String useDesc;

    /**
     * 用途名
     */
    @Column(name = "use_title")
    private String useTitle;

    /**
     * 类别
     */
    @Column(name = "model")
    private String model;

    /**
     * 用途类型（0化学 1物理 2计算机，3金工，4电路，5土木，7航天航空，8食品，9药物，10材料）
     */
    @Column(name = "type")
    private String type;

    /**
     * （0代表存在 1代表启用）
     */
    @Column(name = "status")
    private String status;

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
