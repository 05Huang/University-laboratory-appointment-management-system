package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.commons.exception.BadRequestException;
import com.mafei.laboratory.system.entity.SysUser;
import com.mafei.laboratory.system.entity.vo.LoginUserVo;
import com.mafei.laboratory.system.entity.vo.UserVo;
import com.mafei.laboratory.system.repository.SysUserRepository;
import com.mafei.laboratory.system.service.SysUserService;
import com.mafei.laboratory.system.service.dto.LoginDto;
import com.mafei.laboratory.system.service.dto.UserDto;
import com.mafei.laboratory.system.service.dto.ChangePwdDto;
import com.mafei.laboratory.system.service.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author wutangsheng
 * @create 2021-02-18 14:13
 * @info
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements SysUserService {
    private final SysUserRepository userRepository;
    private final String status = "1";

    @Override
    public LoginUserVo queryByUsername(LoginDto userDto) {
        SysUser user = userRepository.findByLoginName(userDto.getUsername());
        if (user == null) {
            throw new BadRequestException("用户不存在");
        }

        Long roleId = userRepository.queryRoleId(user.getUserId());
        if (!user.getPassword().equals(userDto.getPassword())) {
            throw new BadRequestException("用户名或密码错误");
        }

        if (status.equals(user.getStatus())) {
            throw new BadRequestException(HttpStatus.FORBIDDEN, "该账号已停用");
        }

        LoginUserVo loginUserVo = new LoginUserVo();
        //复制类
        BeanUtils.copyProperties(user, loginUserVo);
        loginUserVo.setRoleId(roleId);
        return loginUserVo;
    }

    @Override
    public SysUser queryById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public List<SysUser> queryAllByLimit(int offset, int limit) {
        return null;
    }

    @Override
    public void insert(UserDto userDto) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userDto, user);

        userRepository.save(user);

    }

    @Override
    public void update(UserDto userDto) {
        SysUser user = userRepository.findByUserId(userDto.getUserId());
        try {
            user.setUserName(userDto.getUserName());
            user.setEmail(userDto.getEmail());
            user.setPhonenumber(userDto.getPhonenumber());
            user.setSex(userDto.getSex());
            user.setStatus(userDto.getStatus());
            userRepository.save(user);
        } catch (BadRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(String status, Set<Long> ids) {
        try {
            if (ids.contains(1)) {
                ids.remove(1);
            }
            List<SysUser> list = userRepository.queryByIds(ids);
            for (SysUser user : list) {
                user.setStatus(status);
            }
            userRepository.saveAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean deleteById(Long id) {
        Integer i = userRepository.deleteByUserId(id);
        return i == 0;
    }

    @Override
    public void deleteByIds(Set<Long> userId) {
        try {
            userRepository.deleteByUserIds(userId);
        } catch (BadRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<UserVo> queryAll() {
        List<SysUser> list = userRepository.findAll();
        List<UserVo> userVoList = new ArrayList<>(list.size());
        for (SysUser user : list) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            if ("0".equals(user.getSex())) {
                userVo.setSex("男");
            } else if ("1".equals(user.getSex())) {
                userVo.setSex("女");
            } else {
                userVo.setSex("未知");
            }

            if ("0".equals(user.getStatus())) {
                userVo.setStatus("正常");
            } else {
                userVo.setStatus("停用");
            }

            userVoList.add(userVo);
        }
        return userVoList;
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, ChangePwdDto changePwdDto) {
        // 验证参数
        if (userId == null) {
            throw new BadRequestException("用户ID不能为空");
        }
        if (!changePwdDto.getNewPassword().equals(changePwdDto.getConfirmPassword())) {
            throw new BadRequestException("两次输入的新密码不一致");
        }

        // 获取用户信息
        SysUser user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new BadRequestException("用户不存在");
        }

        // 验证旧密码
        if (!user.getPassword().equals(changePwdDto.getOldPassword())) {
            throw new BadRequestException("旧密码错误");
        }

        // 更新密码
        user.setPassword(changePwdDto.getNewPassword());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserInfo(Long userId, UserInfoDto userInfoDto) {
        SysUser user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("用户不存在"));
        
        user.setUserName(userInfoDto.getUserName());
        user.setEmail(userInfoDto.getEmail());
        user.setRemark(userInfoDto.getRemark());
        user.setUpdateTime(new Date());
        
        userRepository.save(user);
    }
}
