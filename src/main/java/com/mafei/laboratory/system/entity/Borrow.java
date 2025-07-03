package com.mafei.laboratory.system.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wutangsheng
 * @create 2021-03-11 19:51
 * @info
 */
@Getter
@Setter
public class Borrow {
    private Long id;
    private Long userId;
    private String userName;
    private String status;
    private String borrowStatus;
    private String comment;
    private Date createTime;

    public Borrow() {
    }

    public Borrow(Long id, Long userId, String userName, String status, String borrowStatus, String comment, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.status = status;
        this.borrowStatus = borrowStatus;
        this.comment = comment;
        this.createTime = createTime;
    }
}
