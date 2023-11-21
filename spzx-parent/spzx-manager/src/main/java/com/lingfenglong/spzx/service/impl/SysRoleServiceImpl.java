package com.lingfenglong.spzx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.mapper.SysRoleMapper;
import com.lingfenglong.spzx.model.dto.system.AssignMenuDto;
import com.lingfenglong.spzx.model.dto.system.SysRoleDto;
import com.lingfenglong.spzx.model.entity.system.SysRole;
import com.lingfenglong.spzx.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    private SysRoleMapper sysRoleMapper;
    @Override
    public PageInfo<SysRole> findSysRolePage(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> sysRoleList = sysRoleMapper.findSysRolePage(sysRoleDto);
        return new PageInfo<>(sysRoleList);
    }

    @Override
    public void save(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }

    @Override
    public void remove(Long roleId) {
        sysRoleMapper.remove(roleId);
    }

    @Override
    public void update(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }

    @Override
    public void removeAllMenu(Long roleId) {
        sysRoleMapper.removeAllMenu(roleId);
    }

    @Override
    public void assignRoleMenu(AssignMenuDto assignMenuDto) {
        sysRoleMapper.assignRoleMenu(assignMenuDto);
    }

    @Autowired
    public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }
}
