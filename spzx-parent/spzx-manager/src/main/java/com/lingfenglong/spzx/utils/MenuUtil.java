package com.lingfenglong.spzx.utils;

import com.lingfenglong.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuUtil {
    private List<SysMenu> menuList;

    private MenuUtil() {
    }

    public static MenuUtil builder() {
        return new MenuUtil();
    }

    public List<SysMenu> buildMenuTree() {
        List<SysMenu> menus = new ArrayList<>();
        menuList.stream()
                .filter(menu -> menu.getParentId() == 0)
                .forEach(menu -> menus.add(buildMenuTreeHelper(menu)));

        return menus;
    }

    private SysMenu buildMenuTreeHelper(SysMenu sysMenu) {
        List<SysMenu> children = new ArrayList<>();
        menuList.stream()
                .filter(menu -> menu.getParentId().equals(sysMenu.getId()))
                .forEach(menu -> children.add(buildMenuTreeHelper(menu)));

        sysMenu.setChildren(children);
        return sysMenu;
    }

    public MenuUtil setMenuList(List<SysMenu> menuList) {
        this.menuList = menuList;
        return this;
    }
}
