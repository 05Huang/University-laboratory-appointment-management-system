package com.mafei.laboratory.system.service.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserInfoDto {
    
    @NotBlank(message = "用户名不能为空")
    private String userName;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    private String remark;
} 