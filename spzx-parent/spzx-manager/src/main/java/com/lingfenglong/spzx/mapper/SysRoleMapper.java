package com.lingfenglong.spzx.mapper;

import com.lingfenglong.spzx.model.dto.system.AssignMenuDto;
import com.lingfenglong.spzx.model.dto.system.SysRoleDto;
import com.lingfenglong.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    // 查询所有角色
    List<SysRole> findAllRoles();

    // 根据用户Id查询该用户所具有的角色的全部Id
    List<Long> findRoleIdsByUserId(@Param("userId") Long userId);

    // 移除该角色的所有菜单
    void removeAllMenu(@Param("roleId") Long roleId);

    // 为一个角色分配菜单
    void assignRoleMenu(AssignMenuDto assignMenuDto);
}
