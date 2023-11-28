/*
 * @Descripttion:
 * @version:
 * @Date: 2021-04-20 11:06:21
 * @LastEditors: huzhushan@126.com
 * @LastEditTime: 2021-07-26 13:37:30
 * @Author: huzhushan@126.com
 * @HomePage: https://huzhushan.gitee.io/vue3-element-admin
 * @Github: https://github.com/huzhushan/vue3-element-admin
 * @Donate: https://huzhushan.gitee.io/vue3-element-admin/donate/
 */
import request from '@/utils/request'

const prefix = '/admin/system/sysMenu'

// 获取所有菜单
export const GetMenus = () => {
  return request({
    url: `${prefix}/`,
    method: 'get',
  })
}

// 删除一个底级菜单
export const RemoveMenu = (menuDto) => {
  return request({
    url: `${prefix}/`,
    method: 'delete',
    data: menuDto,
  })
}

// 添加菜单
export const AddMenu = (menuDto) => {
  return request({
    url: `${prefix}/`,
    method: 'post',
    data: menuDto
  })
}

// 修改菜单
export const UpdateMenu = (menuDto) => {
  return request({
    url: `${prefix}/`,
    method: 'put',
    data: menuDto
  })
}

// 获取菜单数列表
export const GetSysMenuTreeList = (roleId) => {
  return request({
    url: `${prefix}/${roleId}`,
    method: 'get',
  })
}