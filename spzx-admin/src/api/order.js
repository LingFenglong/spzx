import request from '@/utils/request'

const prefix = '/admin/order/orderInfo'

export const GetOrderStatisticsData = orderStatisticsDto => {
  return request({
    url: `${prefix}/getOrderStatisticsData`,
    method: 'POST',
    data: orderStatisticsDto,
  })
}
