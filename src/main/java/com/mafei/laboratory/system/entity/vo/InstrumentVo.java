package com.mafei.laboratory.system.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wutangsheng
 * @create 2021-03-10 17:58
 * @info
 */
@Data
public class InstrumentVo {
    private Long id;
    private Long depotId;
    private String instrumentName;
    private String model;
    private String type;
    private String status;
    private BigDecimal price;
    private String createBy;
    private String image;  // 设备图片路径
}
