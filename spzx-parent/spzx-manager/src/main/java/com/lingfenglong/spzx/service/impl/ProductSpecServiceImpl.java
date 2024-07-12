package com.lingfenglong.spzx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.mapperplus.ProductSpecMapper;
import com.lingfenglong.spzx.model.entity.product.ProductSpec;
import com.lingfenglong.spzx.service.ProductSpecService;
import org.springframework.stereotype.Service;

@Service
public class ProductSpecServiceImpl extends ServiceImpl<ProductSpecMapper, ProductSpec> implements ProductSpecService {

}
