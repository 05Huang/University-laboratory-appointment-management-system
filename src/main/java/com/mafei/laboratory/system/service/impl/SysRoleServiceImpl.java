package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.system.entity.SysMenu;
import com.mafei.laboratory.system.entity.SysRole;
import com.mafei.laboratory.system.entity.vo.RoleVo;
import com.mafei.laboratory.system.repository.SysRoleRepository;
import com.mafei.laboratory.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author wutangsheng
 * @create 2021-02-23 18:10
 * @info
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysRoleServiceImpl implements SysRoleService {
    private final SysRoleRepository repository;

    @Override
    public List<RoleVo> queryAll() {
        List<SysRole> all = repository.findAll();
        ArrayList<RoleVo> roleVos = new ArrayList<>(all.size());
        for (SysRole sysRole : all) {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(sysRole, roleVo);
            roleVos.add(roleVo);
        }
        return roleVos;
    }

    @Override
    public RoleVo queryById(Long roleId) {
        SysRole byRoleId = repository.findByRoleId(roleId);
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(byRoleId, roleVo);
        return roleVo;
    }

    @Override
    public List<SysRole> queryAllByLimit(int offset, int limit) {
        return null;
    }

    @Override
    public Integer insert(RoleVo roleVo) {
        try {
            SysRole sysRole = new SysRole();
            BeanUtils.copyProperties(roleVo, sysRole);
            repository.save(sysRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public Integer deleteById(Long roleId) {
        return repository.deleteByRoleId(roleId);
    }

    @Override
    public void deleteByIds(Set<Long> userId) {
        repository.deleteByIds(userId);
    }

    @Override
    public Integer update(RoleVo roleVo) {
        try {
            SysRole role = repository.findByRoleId(roleVo.getRoleId());
            role.setRoleName(roleVo.getRoleName());
            role.setRoleKey(roleVo.getRoleKey());
            role.setRoleSort(roleVo.getRoleSort());
            role.setStatus(roleVo.getStatus());
            repository.save(role);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void updateStatus(String status, Set<Long> ids) {
        try {
            List<SysRole> list = repository.queryByIds(ids);
            for (SysRole role : list) {
                role.setStatus(status);
            }
            repository.saveAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
