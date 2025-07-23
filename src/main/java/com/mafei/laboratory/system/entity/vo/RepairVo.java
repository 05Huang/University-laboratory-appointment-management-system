package com.mafei.laboratory.system.entity.vo;

import com.mafei.laboratory.system.entity.Instrument;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wutangsheng
 * @create 2021-03-11 15:01
 * @info
 */
@Getter
@Setter
public class RepairVo extends Instrument {
    private BigDecimal price;
    private String repairPosition;
    private Date createTime;
    private Date updateTime;
}
