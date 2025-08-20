package com.mafei.laboratory.system.entity.vo;

import com.mafei.laboratory.system.entity.Instrument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wutangsheng
 * @create 2021-03-11 14:18
 * @info
 */
@Getter
@Setter
public class PositionVo extends Instrument {
    private String position;
    private String detailNumber;
    private String repairPosition;
    private Date createTime;
    private String image;  // 设备图片路径
}
