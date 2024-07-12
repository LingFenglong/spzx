package com.lingfenglong.spzx.service.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.model.entity.product.ProductSpec;
import com.lingfenglong.spzx.service.product.mapper.ProductSpecMapper;
import com.lingfenglong.spzx.service.product.service.ProductSpecService;
import org.springframework.stereotype.Service;

@Service
public class ProductSpecServiceImpl
        extends ServiceImpl<ProductSpecMapper, ProductSpec>
        implements ProductSpecService {
}
