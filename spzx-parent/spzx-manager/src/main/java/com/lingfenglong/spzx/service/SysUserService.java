package com.lingfenglong.spzx.service;

import com.lingfenglong.spzx.model.dto.system.LoginDto;
import com.lingfenglong.spzx.model.vo.h5.UserInfoVo;
import com.lingfenglong.spzx.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    UserInfoVo getUserInfo(String token);

    void logout(String token);
}
