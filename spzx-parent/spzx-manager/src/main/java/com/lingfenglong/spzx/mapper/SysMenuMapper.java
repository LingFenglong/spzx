package com.lingfenglong.spzx.mapper;

import com.lingfenglong.spzx.model.dto.system.SysMenuDto;
import com.lingfenglong.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysMenuMapper {
    List<SysMenu> findAll();

    void removeMenu(SysMenuDto sysMenuDto);

    void updateMenu(SysMenu sysMenu);

    void saveMenu(SysMenu sysMenu);

    SysMenu findMenuById(@Param("menuId") Long menuId);

    Long countMenuByParentId(@Param("menuId") Long menuId);

    List<Long> findMenuIdsByRoleId(@Param("roleId") Long roleId);
}
