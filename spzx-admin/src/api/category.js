import request from "@/utils/request";

const prefix = '/admin/system/category'

export const FindCategoryListByParentId = (id) => {
  return request({
    url: `${prefix}/${id}`,
  })
}

export const ExportCategoryData = () => {
  return request({
    url: `${prefix}/export`
  })
}
