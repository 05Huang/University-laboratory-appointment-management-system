package com.mafei.laboratory.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 设备借用表
 *
 * @author wts
 */
@Entity
@Table(name = "sys_borrow_instrument")
@Data
public class SysBorrowInstrument {

    /**
     * 仪器id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "instrument_id", nullable = false)
    private Long instrumentId;

    /**
     * 0已审核，1未审核，2驳回,3已归还
     */
    @Column(name = "status")
    private String status;

    /**
     * 0未归还，1异常，8归还,3取消，4被驳回，5待审核,6成功
     */
    @Column(name = "borrow_status")
    private String borrowStatus;

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
     * 借用时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 归还时间
     */
    @Column(name = "update_time")
    private Date updateTime;

}
