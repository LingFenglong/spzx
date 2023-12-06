package com.lingfenglong.spzx.controller;

import com.lingfenglong.spzx.model.dto.system.LoginDto;
import com.lingfenglong.spzx.model.entity.system.SysUser;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.model.vo.common.SysUserResultCode;
import com.lingfenglong.spzx.model.vo.system.LoginVo;
import com.lingfenglong.spzx.model.vo.system.SysMenuVo;
import com.lingfenglong.spzx.model.vo.system.ValidateCodeVo;
import com.lingfenglong.spzx.service.SysUserService;
import com.lingfenglong.spzx.service.ValidateCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户登录接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    private final SysUserService sysUserService;
    private final ValidateCodeService validateCodeService;

    public IndexController(SysUserService sysUserService, ValidateCodeService validateCodeService) {
        this.sysUserService = sysUserService;
        this.validateCodeService = validateCodeService;
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, SysUserResultCode.LOGIN_SUCCESS);
    }

    @Operation(summary = "生成验证码")
    @GetMapping("/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/getUserInfo")
    public Result<SysUser> getUserInfo(@RequestHeader String token) {
        SysUser userInfoVo = sysUserService.getUserInfo(token);
        return Result.build(userInfoVo, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "登出")
    @GetMapping("/logout")
    public Result<?> logout(@RequestHeader String token) {
        sysUserService.logout(token);
        return Result.build(null, SysUserResultCode.LOGOUT_SUCCESS);
    }

    @Operation(summary = "获得用户菜单")
    @GetMapping("/menus")
    public Result<List<SysMenuVo>> menus() {
        return Result.build(sysUserService.findMenusByUserId(), CommonResultCode.SUCCESS);
    }
}
