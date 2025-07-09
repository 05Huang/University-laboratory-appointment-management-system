package com.mafei.laboratory.system.service;

import com.mafei.laboratory.system.entity.SysUser;
import com.mafei.laboratory.system.entity.vo.LoginUserVo;
import com.mafei.laboratory.system.entity.vo.UserVo;
import com.mafei.laboratory.system.service.dto.LoginDto;
import com.mafei.laboratory.system.service.dto.UserDto;
import com.mafei.laboratory.system.service.dto.ChangePwdDto;
import com.mafei.laboratory.system.service.dto.UserInfoDto;

import java.util.List;
import java.util.Set;

/**
 * 用户信息表(SysUser)表服务接口
 *
 * @author wutangsheng
 * @since 2021-02-18 11:08:00
 */
public interface SysUserService {

    /**
     * 通过用户名查单条数据
     * @param userDto
     * @return
     */
    LoginUserVo queryByUsername(LoginDto userDto);

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    SysUser queryById(Long userId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    void insert(UserDto sysUser);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    void update(UserDto sysUser);

    /**
     * 批量更新
     * @param status
     * @param ids
     */
    void updateStatus(String status, Set<Long> ids);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Long userId);

    /**
     * 批量删除
     *
     * @param userId 主键
     * @return 是否成功
     */
    void deleteByIds(Set<Long> userId);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<UserVo> queryAll();

    /**
     * 修改用户密码
     *
     * @param userId 用户ID
     * @param changePwdDto 修改密码DTO
     */
    void updatePassword(Long userId, ChangePwdDto changePwdDto);

    /**
     * 更新用户个人信息
     * @param userId 用户ID
     * @param userInfoDto 用户信息
     */
    void updateUserInfo(Long userId, UserInfoDto userInfoDto);
}
