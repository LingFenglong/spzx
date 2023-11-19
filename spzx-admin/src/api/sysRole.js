import request from '@/utils/request'
const prefix = '/admin/system/sysRole'

// 保存系统角色
export const SaveSysRole = (sysRole) => {
  return request({
    url: `${prefix}/save`,
    method: 'post',
    data: sysRole
  })
}

// 删除系统角色
export const RemoveSysRole = (roleId) => {
  return request({
    url: `${prefix}/remove/${roleId}`,
    method: 'get'
  })
}

// 更新系统角色
export const UpdateSysRole = (sysRole) => {
  return request({
    url: `${prefix}/update`,
    method: 'post',
    data: sysRole
  })
}

// 查询系统角色
export const FindSysRolePage = (pageNum, pageSize, queryDto) => {
  return request({
    url: `${prefix}/findPage/${pageNum}/${pageSize}`,
    method: 'post',
    data: queryDto
  })
}