package com.mafei.laboratory.system.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author wutangsheng
 * @create 2021-02-20 14:08
 * @info
 */
@Data
public class UserVo {
    private Long userId;
    private String loginName;
    private String userName;
    private String email;
    private String phonenumber;
    private String sex;
    private String status;
    private Date loginDate;
}
