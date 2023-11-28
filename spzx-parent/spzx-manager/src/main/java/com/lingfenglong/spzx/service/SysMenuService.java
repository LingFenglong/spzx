package com.lingfenglong.spzx.service;

import com.lingfenglong.spzx.model.dto.system.AssignMenuDto;
import com.lingfenglong.spzx.model.dto.system.SysMenuDto;
import com.lingfenglong.spzx.model.entity.system.SysMenu;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> findAll();

    void assignRoleMenu(AssignMenuDto assignMenuDto);

    void removeMenu(SysMenuDto sysMenuDto);

    void updateMenu(SysMenu sysMenu);

    void saveMenu(SysMenuDto sysMenuDto);

    Long countMenuByParentId(Long menuId);

    List<Long> findMenuIdsByRoleId(Long roleId);
}
