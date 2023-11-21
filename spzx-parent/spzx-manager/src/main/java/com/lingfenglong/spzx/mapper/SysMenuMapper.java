package com.lingfenglong.spzx.mapper;

import com.lingfenglong.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    List<SysMenu> findAll();
}
