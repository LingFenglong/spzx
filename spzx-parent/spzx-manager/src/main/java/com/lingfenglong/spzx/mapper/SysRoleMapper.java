package com.lingfenglong.spzx.mapper;

import com.lingfenglong.spzx.model.dto.system.SysRoleDto;
import com.lingfenglong.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    // 通过角色名分页模糊查询
    List<SysRole> findSysRolePage(SysRoleDto sysRoleDto);
}
