import request from '@/utils/request'
const prefix = '/admin/system/sysRole'

export const SaveSysRole = (sysRole) => {
  return request({
    url: `${prefix}/save`,
    method: 'post',
    data: sysRole
  })
}

export const RemoveSysRole = (roleId) => {
  return request({
    url: `${prefix}/remove/${roleId}`,
    method: 'get'
  })
}

export const UpdateSysRole = (sysRole) => {
  return request({
    url: `${prefix}/update`,
    method: 'post',
    data: sysRole
  })
}

export const FindSysRolePage = (pageNum, pageSize, queryDto) => {
  return request({
    url: `${prefix}/findPage/${pageNum}/${pageSize}`,
    method: 'post',
    data: queryDto
  })
}