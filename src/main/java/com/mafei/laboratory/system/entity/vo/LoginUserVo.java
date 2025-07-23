package com.mafei.laboratory.system.entity.vo;


import lombok.Data;

/**
 * @author wutangsheng
 * @create 2021-02-18 14:39
 * @info
 */
@Data
public class LoginUserVo {
    /**
     * 用户ID
     */
    private Long userId;


    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户类型（00系统用户 01注册用户）
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像路径
     */
    private String avatar;
}
