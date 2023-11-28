package com.lingfenglong.spzx.service.impl;

import com.lingfenglong.spzx.mapper.SysMenuMapper;
import com.lingfenglong.spzx.model.dto.system.AssignMenuDto;
import com.lingfenglong.spzx.model.dto.system.SysMenuDto;
import com.lingfenglong.spzx.model.entity.system.SysMenu;
import com.lingfenglong.spzx.service.SysMenuService;
import com.lingfenglong.spzx.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    private SysMenuMapper sysMenuMapper;
    private SysRoleService sysRoleService;

    @Override
    public List<SysMenu> findAll() {
        return sysMenuMapper.findAll();
    }

    @Override
    public void assignRoleMenu(AssignMenuDto assignMenuDto) {
        // 删除该角色下所有菜单
        sysRoleService.removeAllMenu(assignMenuDto.getRoleId());

        // 重新分配菜单
        sysRoleService.assignRoleMenu(assignMenuDto);
    }

    @Override
    public void removeMenu(SysMenuDto sysMenuDto) {
        sysMenuMapper.removeMenu(sysMenuDto);
    }

    @Override
    public void updateMenu(SysMenu sysMenu) {
        sysMenuMapper.updateMenu(sysMenu);
    }

    @Override
    public void saveMenu(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setComponent(sysMenuDto.getComponent());
        sysMenu.setTitle(sysMenuDto.getTitle());
        sysMenu.setStatus(sysMenuDto.getStatus());
        sysMenu.setSortValue(sysMenuDto.getSortValue());
        sysMenu.setParentId(sysMenuDto.getParentId());
        sysMenuMapper.saveMenu(sysMenu);
    }

    @Override
    public Long countMenuByParentId(Long menuId) {
        return sysMenuMapper.countMenuByParentId(menuId);
    }

    @Override
    public List<Long> findMenuIdsByRoleId(Long roleId) {
        return sysMenuMapper.findMenuIdsByRoleId(roleId);
    }

    @Autowired
    public void setSysMenuMapper(SysMenuMapper sysMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
    }

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }
}
