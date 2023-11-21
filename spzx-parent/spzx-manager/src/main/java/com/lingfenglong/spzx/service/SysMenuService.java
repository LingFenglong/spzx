package com.lingfenglong.spzx.service;

import com.lingfenglong.spzx.model.dto.system.AssignMenuDto;
import com.lingfenglong.spzx.model.entity.system.SysMenu;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> findAll();

    void assignRoleMenu(AssignMenuDto assignMenuDto);
}
