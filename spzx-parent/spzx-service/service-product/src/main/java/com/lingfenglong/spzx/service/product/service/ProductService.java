package com.lingfenglong.spzx.service.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingfenglong.spzx.model.entity.product.Product;
import com.lingfenglong.spzx.model.vo.h5.ProductItemVo;

public interface ProductService extends IService<Product> {
    ProductItemVo getItem(Long skuId);
}
