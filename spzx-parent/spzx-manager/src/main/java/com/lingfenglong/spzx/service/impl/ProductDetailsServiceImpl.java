package com.lingfenglong.spzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.mapperplus.ProductDetailsMapper;
import com.lingfenglong.spzx.model.entity.product.ProductDetails;
import com.lingfenglong.spzx.service.ProductDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailsServiceImpl
        extends ServiceImpl<ProductDetailsMapper, ProductDetails>
        implements ProductDetailsService {

    @Override
    public ProductDetails getByProductId(Long id) {
        return baseMapper.selectOne(
                new LambdaQueryWrapper<ProductDetails>()
                        .eq(ProductDetails::getProductId, id));
    }
}
