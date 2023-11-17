const Layout = () => import('@/layout/index.vue')
const SysMenu = () => import('@/views/system/SysMenu.vue')
const SysRole = () => import('@/views/system/SysRole.vue')
const SysUser = () => import('@/views/system/SysUser.vue')

export default [
  {
    path: '/system',
    name: 'system',
    component: Layout,
    meta: {
      title: '系统管理'
    },
    icon: 'Location',
    children: [
      {
        path: '/sysRole',
        name: 'sysRole',
        component: SysRole,
        meta: {
          title: '角色管理'
        },
        hidden: false
      },
      {
        path: '/sysUser',
        name: 'sysUser',
        component: SysUser,
        meta: {
          title: '用户管理'
        },
        hidden: false
      },
      {
        path: '/sysMenu',
        name: 'sysMenu',
        component: SysMenu,
        meta: {
          title: '菜单管理'
        },
        hidden: false
      },
    ]
  }
]
