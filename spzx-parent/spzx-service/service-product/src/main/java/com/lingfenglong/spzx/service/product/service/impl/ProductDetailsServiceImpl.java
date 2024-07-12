package com.lingfenglong.spzx.service.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.model.entity.product.ProductDetails;
import com.lingfenglong.spzx.service.product.mapper.ProductDetailsMapper;
import com.lingfenglong.spzx.service.product.service.ProductDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailsServiceImpl
        extends ServiceImpl<ProductDetailsMapper, ProductDetails>
        implements ProductDetailsService {

    @Override
    public ProductDetails getByProductId(Long productId) {
        LambdaQueryWrapper<ProductDetails> wrapper = new LambdaQueryWrapper<ProductDetails>()
                .eq(ProductDetails::getProductId, productId);
        return baseMapper.selectOne(wrapper);
    }
}
