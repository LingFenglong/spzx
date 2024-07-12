package com.lingfenglong.spzx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.mapperplus.ProductSkuMapper;
import com.lingfenglong.spzx.model.entity.product.ProductSku;
import com.lingfenglong.spzx.service.ProductSkuService;
import org.springframework.stereotype.Service;

@Service
public class ProductSkuServiceImpl
    extends ServiceImpl<ProductSkuMapper, ProductSku>
    implements ProductSkuService {

}
