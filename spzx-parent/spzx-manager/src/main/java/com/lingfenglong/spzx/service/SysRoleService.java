package com.lingfenglong.spzx.service;


import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.system.AssignMenuDto;
import com.lingfenglong.spzx.model.dto.system.SysRoleDto;
import com.lingfenglong.spzx.model.entity.system.SysRole;

public interface SysRoleService {
    PageInfo<SysRole> findSysRolePage(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto);

    void save(SysRole sysRole);

    void remove(Long roleId);

    void update(SysRole sysRole);

    void removeAllMenu(Long roleId);

    void assignRoleMenu(AssignMenuDto assignMenuDto);
}
