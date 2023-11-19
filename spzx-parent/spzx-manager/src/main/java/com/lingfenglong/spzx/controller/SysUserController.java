package com.lingfenglong.spzx.controller;

import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.system.SysUserDto;
import com.lingfenglong.spzx.model.entity.system.SysUser;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "系统用户接口")
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    private SysUserService sysUserService;

    @Operation(summary = "分页查询系统用户")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result<PageInfo<SysUser>> findPage(@PathVariable("pageNum") Integer pageNum,
                                              @PathVariable("pageSize") Integer pageSize,
                                              SysUserDto sysUserDto) {
        PageInfo<SysUser> pageInfo = sysUserService.findPage(pageNum, pageSize, sysUserDto);
        return Result.build(pageInfo, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "删除系统用户")
    @DeleteMapping("/{userId}")
    public Result<?> remove(@PathVariable("userId") Long userId) {
        sysUserService.remove(userId);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "保存系统用户")
    @PostMapping("/")
    public Result<?> save(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "修改系统用户")
    @PutMapping("/")
    public Result<?> update(@RequestBody SysUser sysUser) {
        sysUserService.update(sysUser);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }
}
