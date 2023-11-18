package com.lingfenglong.spzx.mapper;

import com.lingfenglong.spzx.model.dto.system.SysRoleDto;
import com.lingfenglong.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    // 通过角色名分页模糊查询
    List<SysRole> findSysRolePage(SysRoleDto sysRoleDto);

    // 保存系统角色
    void save(SysRole sysRole);

    // 删除系统角色
    void remove(@Param("roleId") Long roleId);

    // 修改系统角色
    void update(SysRole sysRole);
}
