package com.lingfenglong.spzx.service.product.utils;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.lingfenglong.spzx.model.entity.product.ProductSku;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSkuOrderStrategy {
    public static final boolean DESC = false;
    public static final boolean ASC = true;

    private int code;
    private String name;
    private boolean asc;
    private List<SFunction<ProductSku, ?>> columns = new ArrayList<>();

    public ProductSkuOrderStrategy(int code, String name, boolean asc, SFunction<ProductSku, ?> column) {
        this.code = code;
        this.name = name;
        this.asc = asc;
        this.columns.add(column);
    }
}
