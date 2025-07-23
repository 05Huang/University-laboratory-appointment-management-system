package com.mafei.laboratory.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户和角色关联表
 * @author wts
 */
@Entity
@Table(name = "sys_user_role")
@Data
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 角色ID
     */
    @Column(name = "role_id", nullable = false)
    private Long roleId;

}
