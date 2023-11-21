import request from '@/utils/request'
const prefix = '/admin/system/sysUser'

// 保存系统用户
export const SaveSysUser = (sysUser) => {
  return request({
    url: `${prefix}/`,
    method: 'post',
    data: sysUser
  })
}

// 删除系统用户
export const RemoveSysUser = (UserId) => {
  return request({
    url: `${prefix}/${UserId}`,
    method: 'delete'
  })
}

// 更新系统用户
export const UpdateSysUser = (sysUser) => {
  return request({
    url: `${prefix}/`,
    method: 'put',
    data: sysUser
  })
}

// 查询系统用户
export const FindSysUserPage = (pageNum, pageSize, queryDto) => {
  return request({
    url: `${prefix}/${pageNum}/${pageSize}`,
    method: 'get',
    params: queryDto
  })
}

// 查询系统用户的角色
export const FindSysUserRoles = (userId) => {
  return request({
    url: `${prefix}/roles/${userId}`,
    method: 'get',
  })
}

// 为系统用户分配角色
export const AssignSysUserRoles = (assignRoleDto) => {
  return request({
    url: `${prefix}/roles`,
    method: 'post',
    data: assignRoleDto
  })
}