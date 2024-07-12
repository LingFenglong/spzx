package com.lingfenglong.spzx.service.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.model.entity.product.Brand;
import com.lingfenglong.spzx.service.product.mapper.BrandMapper;
import com.lingfenglong.spzx.service.product.service.BrandService;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl
        extends ServiceImpl<BrandMapper, Brand>
        implements BrandService {
}
