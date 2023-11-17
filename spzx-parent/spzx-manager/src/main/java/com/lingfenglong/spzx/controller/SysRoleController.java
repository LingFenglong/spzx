package com.lingfenglong.spzx.controller;

import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.system.SysRoleDto;
import com.lingfenglong.spzx.model.entity.system.SysRole;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "系统角色接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    private SysRoleService sysRoleService;

    @Operation(summary = "分页查询系统角色")
    @PostMapping("/findSysRolePage")
    public Result<PageInfo<SysRole>> findSysRolePage(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestBody SysRoleDto sysRoleDto
            ) {
        PageInfo<SysRole> page = sysRoleService.findSysRolePage(pageNum, pageSize, sysRoleDto);
        return Result.build(page, CommonResultCode.SUCCESS);
    }

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }
}
