package com.lingfenglong.spzx.service.impl;

import com.lingfenglong.spzx.mapper.SysMenuMapper;
import com.lingfenglong.spzx.model.dto.system.AssignMenuDto;
import com.lingfenglong.spzx.model.entity.system.SysMenu;
import com.lingfenglong.spzx.service.SysMenuService;
import com.lingfenglong.spzx.service.SysRoleService;
import com.lingfenglong.spzx.utils.MenuUtil;
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

    @Autowired
    public void setSysMenuMapper(SysMenuMapper sysMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
    }

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }
}
