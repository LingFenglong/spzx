package com.lingfenglong.spzx.service.user.controller;

import com.lingfenglong.spzx.model.dto.h5.UserLoginDto;
import com.lingfenglong.spzx.model.dto.h5.UserRegisterDto;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.model.vo.h5.UserInfoVo;
import com.lingfenglong.spzx.service.user.service.impl.UserInfoServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户接口")
@RestController
@RequestMapping("/api/user/userInfo")
public class UserInfoController {

    private final UserInfoServiceImpl userInfoServiceImpl;

    public UserInfoController(UserInfoServiceImpl userInfoServiceImpl) {
        this.userInfoServiceImpl = userInfoServiceImpl;
    }

    @PostMapping("/login")
    public Result<?> login(@Valid @RequestBody UserLoginDto userLoginDto, Errors errors) {
        String token = userInfoServiceImpl.login(userLoginDto);
        return Result.build(token, CommonResultCode.SUCCESS);
    }
    
    @PostMapping("/register")
    public Result<?> register(@RequestBody UserRegisterDto userRegisterDto) {
        userInfoServiceImpl.register(userRegisterDto);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @GetMapping("/auth/getCurrentUserInfo")
    public Result<UserInfoVo> getCurrentUserInfo() {
        UserInfoVo userInfoVo = userInfoServiceImpl.getCurrentUserInfo();
        return Result.build(userInfoVo, CommonResultCode.SUCCESS);
    }
}
