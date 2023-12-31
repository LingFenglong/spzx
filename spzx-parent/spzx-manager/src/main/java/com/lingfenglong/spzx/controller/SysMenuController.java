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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Operation(summary = "获取角色已分配的菜单")
    @GetMapping("/{roleId}")
    public Result<Map<String, Object>> findMenuIdsByRoleId(@PathVariable("roleId") Long roleId) {
        // 获取所有菜单
        List<SysMenu> menus = sysMenuService.findAll();
        // 将菜单的格式变为所需的
        menus = MenuUtil.builder()
                .setMenuList(menus)
                .buildMenuTree();

        // 获得已分配的menuId
        List<Long> menuIds = sysMenuService.findMenuIdsByRoleId(roleId);

        Map<String, Object> map = new HashMap<>();
        map.put("allMenu", menus);
        map.put("assignedMenu", menuIds);
        return Result.build(map, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "删除底级菜单")
    @DeleteMapping("/")
    public Result<?> removeMenu(@RequestBody SysMenuDto sysMenuDto) {
        Long count = sysMenuService.countMenuByParentId(sysMenuDto.getMenuId());
        if (count > 0) {
            return Result.build(null, CommonResultCode.NODE_ERROR);
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
    public Result<?> saveMenu(@RequestBody SysMenuDto sysMenuDto) {
        sysMenuService.saveMenu(sysMenuDto);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }
}
