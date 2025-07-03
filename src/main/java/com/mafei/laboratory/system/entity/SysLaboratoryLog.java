package com.mafei.laboratory.system.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色和菜单关联表
 * @author wts
 */
@Entity
@Data
@Table(name = "sys_laboratory_log")
public class SysLaboratoryLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 实验室ID
     */
    @Column(name = "laboratory_id", nullable = false)
    private Long laboratoryId;

    /**
     * 状态ID
     */
    @Column(name = "laboratory_status_id", nullable = false)
    private Long laboratoryStatusId;

    /**
     * 预约人ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
