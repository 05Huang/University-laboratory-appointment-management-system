package com.mafei.laboratory.system.entity.vo;

import com.mafei.laboratory.system.entity.Borrow;
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
public class BorrowLaboratoryVo {
    private Long id;
    private Long userId;
    private Long laboratoryId;
    private String userName;
    private String status;
    private String borrowStatus;
    private String comment;
    private Date createTime;
    private String laboratoryName;

    public BorrowLaboratoryVo() {
    }

    public BorrowLaboratoryVo(Long id, Long userId, Long laboratoryId, String userName, String status, String borrowStatus, String comment, Date createTime, String laboratoryName) {
        this.id = id;
        this.userId = userId;
        this.laboratoryId = laboratoryId;
        this.userName = userName;
        this.status = status;
        this.borrowStatus = borrowStatus;
        this.comment = comment;
        this.createTime = createTime;
        this.laboratoryName = laboratoryName;
    }
}
