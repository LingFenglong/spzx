package com.lingfenglong.spzx.service.product.utils;

import com.lingfenglong.spzx.model.entity.product.ProductSku;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductSkuOrderStrategyContainer {
    private final Map<Integer, ProductSkuOrderStrategy> orderStrategyMap = new HashMap<>();

    {
        orderStrategyMap.put(1, new ProductSkuOrderStrategy(1, "综合排序", ProductSkuOrderStrategy.DESC, ProductSku::getSaleNum));
        orderStrategyMap.put(2, new ProductSkuOrderStrategy(2, "价格升序", ProductSkuOrderStrategy.ASC, ProductSku::getMarketPrice));
        orderStrategyMap.put(3, new ProductSkuOrderStrategy(3, "价格降序", ProductSkuOrderStrategy.DESC, ProductSku::getMarketPrice));
    }

    public ProductSkuOrderStrategy getStrategy(Integer code) {
        return orderStrategyMap.get(code);
    }
}
