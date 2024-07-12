package com.lingfenglong.spzx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingfenglong.spzx.model.entity.product.ProductDetails;

public interface ProductDetailsService extends IService<ProductDetails> {
    ProductDetails getByProductId(Long id);
}
