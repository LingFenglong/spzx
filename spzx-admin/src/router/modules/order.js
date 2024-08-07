import Layout from '@/layout/index.vue'
import orderStatistics from '@/views/order/orderStatistics.vue'

export default [
  {
    path: '/order',
    component: Layout,
    name: 'order',
    meta: {
      title: '订单管理',
    },
    icon: 'Operation',
    children: [
      {
        path: '/orderStatistics',
        name: 'orderStatistics',
        component: orderStatistics,
        meta: {
          title: '订单统计',
        },
      },
    ],
  },
]
