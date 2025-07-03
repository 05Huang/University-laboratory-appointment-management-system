package com.mafei.laboratory.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 仪器位置表
 */
@Entity
@Data
@Table(name = "sys_instrument_position")
public class SysInstrumentPosition implements Serializable {

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
     * 地点名
     */
    @Column(name = "position")
    private String position;

    /**
     * 详细地点（箱号，柜号等）
     */
    @Column(name = "detail_number")
    private String detailNumber;

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
