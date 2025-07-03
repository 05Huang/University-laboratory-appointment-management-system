package com.mafei.laboratory.system.entity;

import lombok.Data;

/**
 * @author wutangsheng
 * @create 2021-03-11 15:03
 * @info
 */
@Data
public class Instrument {
    private Long id;
    private Long instrumentId;
    private String instrumentName;
    private String comment;
}
