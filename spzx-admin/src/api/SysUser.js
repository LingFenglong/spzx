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
    method: 'get'
  })
}

// 更新系统用户
export const UpdateSysUser = (sysUser) => {
  return request({
    url: `${prefix}/`,
    method: 'post',
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