package com.lingfenglong.spzx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.mapperplus.ProductUnitMapper;
import com.lingfenglong.spzx.model.entity.base.ProductUnit;
import com.lingfenglong.spzx.service.ProductUnitService;
import org.springframework.stereotype.Service;

@Service
public class ProductUnitServiceImpl
        extends ServiceImpl<ProductUnitMapper, ProductUnit>
        implements ProductUnitService {

}
