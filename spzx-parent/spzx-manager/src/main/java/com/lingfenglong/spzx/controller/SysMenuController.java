package com.lingfenglong.spzx.controller;

import com.lingfenglong.spzx.model.dto.system.SysMenuDto;
import com.lingfenglong.spzx.model.entity.system.SysMenu;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.SysMenuService;
import com.lingfenglong.spzx.utils.MenuUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "菜单管理接口")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    private SysMenuService sysMenuService;

    @Operation(summary = "获取所有菜单")
    @GetMapping("/")
    public Result<List<SysMenu>> getAll() {
        // 获取所有菜单
        List<SysMenu> menus = sysMenuService.findAll();
        // 将菜单的格式变为所需的
        menus = MenuUtil.builder()
                .setMenuList(menus)
                .buildMenuTree();
        return Result.build(menus, CommonResultCode.SUCCESS);
    }

    //@Operation(summary = "为角色分配菜单")
    //@PutMapping("/assign")
    //public Result<List<SysMenu>> assignRoleMenu(@RequestBody AssignMenuDto assignMenuDto) {
    //    sysMenuService.assignRoleMenu(assignMenuDto);
    //    return Result.build(null, CommonResultCode.SUCCESS);
    //}

    @Operation(summary = "删除底级菜单")
    @DeleteMapping("/")
    public Result<?> removeMenu(@RequestBody SysMenuDto sysMenuDto) {
        Long count = sysMenuService.countMenuByParentId(sysMenuDto.getMenuId());
        if (count > 0) {
            Result.build(null, CommonResultCode.NODE_ERROR);
        }
        sysMenuService.removeMenu(sysMenuDto);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "修改菜单")
    @PutMapping("/")
    public Result<?> updateMenu(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateMenu(sysMenu);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "添加菜单")
    @PostMapping("/")
    public Result<?> saveMenu(@RequestBody SysMenu sysMenu) {
        sysMenuService.saveMenu(sysMenu);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }
}
