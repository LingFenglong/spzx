package com.lingfenglong.spzx.mapperplus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingfenglong.spzx.model.entity.order.OrderInfo;
import com.lingfenglong.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Select;

public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    @Select("""
select
    date_sub(date(now()), INTERVAL 1 DAY) as orderDate,
    sum(total_amount) as totalAmount,
    count(*) as totalNum
from
    db_spzx.order_info
where
    date(create_time) = date_sub(date(now()), INTERVAL 1 DAY)
    and is_deleted = 0
""")
    OrderStatistics getOrderStatisticsYesterday();
}
