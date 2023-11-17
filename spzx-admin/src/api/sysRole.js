import request from '@/utils/request'

export const SaveSysRole = (sysRole) => {
  return request({
    url: ``,
    method: 'post',
    data: sysRole
  })
}

export const RemoveSysRole = (sysRole) => {
  return request({
    url: ``,
    method: 'delete',
    data: sysRole
  })
}

export const UpdateSysRole = (sysRole) => {
  return request({
    url: ``,
    method: 'put',
    data: sysRole
  })
}

export const FindSysRolePage = (pageNum, pageSize, queryDto) => {
  return request({
    url: ``,
    method: 'get',
    data: sysRole
  })
}