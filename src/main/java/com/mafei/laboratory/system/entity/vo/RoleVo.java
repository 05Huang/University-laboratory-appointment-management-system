package com.mafei.laboratory.system.entity.vo;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author wutangsheng
 * @create 2021-02-23 18:07
 * @info
 */
@Data
public class RoleVo {
    private Long roleId;

    private String roleName;

    private String roleKey;

    private Integer roleSort;

    private String status;
}
