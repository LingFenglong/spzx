package com.lingfenglong.spzx.model.entity.order;


import com.lingfenglong.spzx.model.entity.base.BaseEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class OrderStatistics extends BaseEntity {
    private Date orderDate;
    private BigDecimal totalAmount;
    private Integer totalNum;
}