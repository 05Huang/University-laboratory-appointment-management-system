package com.mafei.laboratory.page.vo;

import lombok.Data;

/**
 * @author wutangsheng
 * @create 2021-03-11 17:14
 * @info
 */
@Data
public class TypeVo {
    private Integer type;
    private String value;

    public TypeVo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
