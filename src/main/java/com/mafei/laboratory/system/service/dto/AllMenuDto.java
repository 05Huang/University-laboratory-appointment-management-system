package com.mafei.laboratory.system.service.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author wutangsheng
 * @create 2021-02-23 17:28
 * @info
 */
@Data
public class AllMenuDto {
    private Long menuId;

    private Long parentId;

    private String menuName;

    private Integer orderNum;

    private String url;

    private String target;

    private String visible;

    private String icon;

    private String createBy;

    private Date createTime;

    private String remark;
}
