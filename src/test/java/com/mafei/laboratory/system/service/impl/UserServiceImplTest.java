package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.system.entity.vo.LoginUserVo;
import com.mafei.laboratory.system.service.SysUserService;
import com.mafei.laboratory.system.service.dto.LoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wutangsheng
 * @create 2021-02-18 15:08
 * @info
 */
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    SysUserService userService;

    @Test
    void queryByUsername() {
        LoginDto userDto = new LoginDto();
        userDto.setUsername("admin");
        userDto.setPassword("123456");
        userDto.setCaptcha("1");

        LoginUserVo loginUserVo = userService.queryByUsername(userDto);
        System.out.println(loginUserVo);
    }
}