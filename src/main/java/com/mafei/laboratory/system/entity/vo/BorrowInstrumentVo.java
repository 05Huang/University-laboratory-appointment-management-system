package com.mafei.laboratory.system.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wutangsheng
 * @create 2021-03-11 19:53
 * @info
 */
@Getter
@Setter
public class BorrowInstrumentVo{
    private Long id;
    private Long userId;
    private Long instrumentId;
    private String userName;
    private String instrumentName;
    private String status;
    private String borrowStatus;
    private String comment;
    private Date createTime;

    public BorrowInstrumentVo() {
    }

    public BorrowInstrumentVo(Long id, Long userId, Long instrumentId, String userName, String instrumentName, String status, String borrowStatus, String comment, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.instrumentId = instrumentId;
        this.userName = userName;
        this.instrumentName = instrumentName;
        this.status = status;
        this.borrowStatus = borrowStatus;
        this.comment = comment;
        this.createTime = createTime;
    }
}
