package com.lingfenglong.spzx.mapper;

import com.lingfenglong.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper {
    // 根据用户名查询用户
    SysUser selectByUserName(@Param("userName") String userName);
}
