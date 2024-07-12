package com.lingfenglong.spzx.service.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingfenglong.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {
    List<ProductSku> getHotProductList();
}
