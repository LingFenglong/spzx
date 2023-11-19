package com.lingfenglong.spzx.mapper;

import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.system.SysUserDto;
import com.lingfenglong.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper {
    // 根据用户名查询用户
    SysUser selectByUserName(@Param("userName") String userName);

    // 分页查询用户
    List<SysUser> findPage(SysUserDto sysUserDto);

    // 删除用户
    void remove(@Param("userId") Long userId);

    // 添加用户
    void save(SysUser sysUser);

    // 修改用户
    void update(SysUser sysUser);
}
