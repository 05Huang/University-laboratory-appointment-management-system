package com.mafei.laboratory.system.service.impl;

import com.mafei.laboratory.commons.exception.BadRequestException;
import com.mafei.laboratory.system.entity.SysMenu;
import com.mafei.laboratory.system.entity.SysUser;
import com.mafei.laboratory.system.entity.vo.AllMenuVo;
import com.mafei.laboratory.system.entity.vo.MenuVo;
import com.mafei.laboratory.system.repository.SysMenuRepository;
import com.mafei.laboratory.system.repository.SysRoleMenuRepository;
import com.mafei.laboratory.system.service.SysMenuService;
import com.mafei.laboratory.system.service.dto.AllMenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wutangsheng
 * @create 2021-02-19 12:37
 * @info
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SysMenuServiceImpl implements SysMenuService {
    private final SysMenuRepository menuRepository;
    private final SysRoleMenuRepository sysRoleMenuRepository;


    @Override
    public AllMenuVo queryById(Long menuId) {
        SysMenu sysMenu = menuRepository.findByMenuId(menuId);
        AllMenuVo allMenuVo = new AllMenuVo();
        BeanUtils.copyProperties(sysMenu, allMenuVo);
        return allMenuVo;
    }

    @Override
    public List<SysMenu> queryAllByLimit(int offset, int limit) {
        return null;
    }

    @Override
    public Integer insert(AllMenuVo sysMenu) {
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(sysMenu, menu);
        menuRepository.save(menu);
        return 1;
    }

    @Override
    public Integer update(AllMenuDto sysMenu) {
        try {
            SysMenu menu = menuRepository.findByMenuId(sysMenu.getMenuId());
            menu.setParentId(sysMenu.getParentId());
            menu.setMenuName(sysMenu.getMenuName());
            menu.setOrderNum(sysMenu.getOrderNum());
            menu.setUrl(sysMenu.getUrl());
            menu.setTarget(sysMenu.getTarget());
            menu.setVisible(sysMenu.getVisible());
            menu.setIcon(sysMenu.getIcon());
            menu.setRemark(sysMenu.getRemark());

            menuRepository.save(menu);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void updateStatus(String status, Set<Long> ids) {
        try {
            List<SysMenu> list = menuRepository.queryByIds(ids);
            for (SysMenu menu : list) {
                menu.setVisible(status);
            }
            menuRepository.saveAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer deleteById(Long menuId) {
        menuRepository.deleteByMenuId(menuId);
        return 1;
    }

    @Override
    public void deleteByIds(Set<Long> ids) {
        try {
            menuRepository.deleteByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MenuVo> queryMenuByRole(Long roleId) {
        List<Long> menuIds = sysRoleMenuRepository.findByRoleId(roleId);
        List<SysMenu> menus = menuRepository.findMenu(menuIds);
        //查询父节点菜单
        List<MenuVo> rootMenu = menus.stream()
                .filter(menu -> menu.getParentId() == 0)
                .map(this::menuToMenuVo)
                .sorted(Comparator.comparing(MenuVo::getOrderNum))
                .collect(Collectors.toList());

        findChildMenu(menus, rootMenu);

        return rootMenu;
    }

    @Override
    public List<AllMenuVo> getAllMenu() {
        List<SysMenu> all = menuRepository.findAll();
        LinkedList<AllMenuVo> allMenuVos = new LinkedList<>();
        for (SysMenu sysMenu : all) {
            AllMenuVo allMenuVo = new AllMenuVo();
            BeanUtils.copyProperties(sysMenu, allMenuVo);
            allMenuVos.add(allMenuVo);
        }
        return allMenuVos;
    }

    private void findChildMenu(List<SysMenu> menuList, List<MenuVo> menuVoList) {
        for (MenuVo menuVo : menuVoList) {
            List<MenuVo> subMenuVoList = new ArrayList<>();
            // 查找子级
            for (SysMenu sysMenu : menuList) {
                if (menuVo.getMenuId().equals(sysMenu.getParentId())) {
                    subMenuVoList.add(menuToMenuVo(sysMenu));
                }
                // 递归调用，不管有几级菜单，都能够适用
                findChildMenu(menuList, subMenuVoList);

                subMenuVoList.sort(Comparator.comparing(MenuVo::getOrderNum));
            }
            menuVo.setChildren(subMenuVoList);
        }
    }

    /**
     * 类转换
     *
     * @param menu
     * @return
     */
    private MenuVo menuToMenuVo(SysMenu menu) {
        MenuVo menuVo = new MenuVo();
        BeanUtils.copyProperties(menu, menuVo);
        return menuVo;
    }

}
