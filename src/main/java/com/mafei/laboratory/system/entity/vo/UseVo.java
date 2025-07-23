package com.mafei.laboratory.system.entity.vo;

import com.mafei.laboratory.system.entity.Instrument;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wutangsheng
 * @create 2021-03-11 15:06
 * @info
 */
@Getter
@Setter
public class UseVo extends Instrument {
    private String useDesc;
    private String useTitle;
    private String type;
    private Date createTime;
}
