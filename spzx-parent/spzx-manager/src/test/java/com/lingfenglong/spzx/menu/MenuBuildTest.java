package com.lingfenglong.spzx.menu;

import com.lingfenglong.spzx.mapper.SysMenuMapper;
import com.lingfenglong.spzx.model.entity.system.SysMenu;
import com.lingfenglong.spzx.utils.MenuUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MenuBuildTest {
    @Autowired
    SysMenuMapper sysMenuMapper;

    @Test
    void menuBuildTest() {
        List<SysMenu> menus = sysMenuMapper.findAll();
        menus.forEach(System.out::println);
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println();

        List<SysMenu> sysMenuList = MenuUtil.builder()
                .setMenuList(menus)
                .buildMenuTree();

        sysMenuList.forEach(menu -> print(menu, 0));
    }

    private void print(SysMenu sysMenu, int index) {
        System.out.println("\t".repeat(index) + sysMenu.getTitle() + "（" + sysMenu.getComponent() + ")");
        if (sysMenu.getChildren() != null && sysMenu.getChildren().size() > 0) {
            sysMenu.getChildren().forEach(menu -> print(menu, index + 1));
        }
    }
}
