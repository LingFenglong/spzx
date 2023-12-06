package com.lingfenglong.spzx.service;

import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.system.AssignRoleDto;
import com.lingfenglong.spzx.model.dto.system.LoginDto;
import com.lingfenglong.spzx.model.dto.system.SysUserDto;
import com.lingfenglong.spzx.model.entity.system.SysUser;
import com.lingfenglong.spzx.model.vo.system.LoginVo;
import com.lingfenglong.spzx.model.vo.system.SysMenuVo;
import com.lingfenglong.spzx.model.vo.system.UserRolesVo;

import java.util.List;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> findPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    void remove(Long userId);

    void save(SysUser sysUser);

    void update(SysUser sysUser);

    UserRolesVo findRolesByUserId(Long userId);

    void assignRolesForUser(AssignRoleDto assignRoleDto);

    List<SysMenuVo> findMenusByUserId();
}
