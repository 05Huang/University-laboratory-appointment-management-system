package com.mafei.laboratory.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 设备借用表
 * @author wts
 */
@Entity
@Data
@Table(name = "sys_borrow_laboratory")
public class SysBorrowLaboratory {

    /**
     * 仪器id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "laboratory_id", nullable = false)
    private Long laboratoryId;

    /**
     * 0未归还，1已归还
     */
    @Column(name = "status")
    private String status;

    /**
     * 0未归还，1已归还
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
