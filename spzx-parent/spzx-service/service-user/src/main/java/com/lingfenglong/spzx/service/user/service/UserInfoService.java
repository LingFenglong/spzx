package com.lingfenglong.spzx.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingfenglong.spzx.model.dto.h5.UserLoginDto;
import com.lingfenglong.spzx.model.dto.h5.UserRegisterDto;
import com.lingfenglong.spzx.model.entity.user.UserAddress;
import com.lingfenglong.spzx.model.entity.user.UserInfo;
import com.lingfenglong.spzx.model.vo.h5.UserInfoVo;

import java.util.List;

public interface UserInfoService extends IService<UserInfo> {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo();
}
