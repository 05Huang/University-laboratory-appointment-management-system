package com.mafei.laboratory.system.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wutangsheng
 * @create 2021-02-19 18:33
 * @info
 */
@Data
public class MenuVo {
    private Long menuId;

    private String menuName;

    private Integer orderNum;

    private String url;

    private String target;

    private String visible;

    private String icon;

    private String remark;

    private List<MenuVo> children;
}
