package com.mafei.laboratory.system.entity.vo;

import lombok.Data;

/**
 * @author wutangsheng
 * @create 2021-03-10 10:54
 * @info
 */
@Data
public class LaboratoryVo {
    private Long id;
    private String laboratoryName;
    private Long depotId;
    private Integer capacity;
    private String target;
    private String useType;
    private String image;
    private String admin;
}
