package com.mafei.laboratory.system.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改密码DTO
 */
@Data
public class ChangePwdDto {
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;

    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
} 