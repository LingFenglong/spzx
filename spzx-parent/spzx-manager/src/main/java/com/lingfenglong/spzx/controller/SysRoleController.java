package com.lingfenglong.spzx.controller;

import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.system.AssignMenuDto;
import com.lingfenglong.spzx.model.dto.system.SysRoleDto;
import com.lingfenglong.spzx.model.entity.system.SysMenu;
import com.lingfenglong.spzx.model.entity.system.SysRole;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "系统角色接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    private SysRoleService sysRoleService;

    @Operation(summary = "分页查询系统角色")
    @PostMapping("/findPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findSysRolePage(
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize,
            @RequestBody SysRoleDto sysRoleDto
            ) {
        PageInfo<SysRole> page = sysRoleService.findSysRolePage(pageNum, pageSize, sysRoleDto);
        return Result.build(page, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "添加系统角色")
    @PostMapping("/save")
    public Result<?> save(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "删除系统角色")
    @GetMapping("/remove/{roleId}")
    public Result<?> remove(@PathVariable("roleId") Long roleId) {
        sysRoleService.remove(roleId);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "修改系统角色")
    @PostMapping("/update")
    public Result<?> update(@RequestBody SysRole sysRole) {
        sysRoleService.update(sysRole);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "为角色分配菜单")
    @PutMapping("/assign")
    public Result<?> assignRoleMenu(@RequestBody AssignMenuDto assignMenuDto) {
        sysRoleService.assignRoleMenu(assignMenuDto);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }
}
