package com.mafei.laboratory.system.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wutangsheng
 * @create 2021-02-20 20:14
 * @info
 */
@Data
public class UserDto {
    private Long userId;
    @NotNull
    private String loginName;
    @NotNull
    private String userName;
    @NotNull
    private String email;
    private String phonenumber;
    private String sex;
    private String status;
}
