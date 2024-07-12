package com.lingfenglong.spzx.service.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.h5.ProductSkuDto;
import com.lingfenglong.spzx.model.entity.product.Product;
import com.lingfenglong.spzx.model.entity.product.ProductSku;

import java.util.List;

public interface ProductSkuService extends IService<ProductSku> {
    List<ProductSku> getHotProductList();

    PageInfo<ProductSku> getProductSkuPage(int pageNum, int pageSize, ProductSkuDto productSkuDto);

    List<ProductSku> getAllByProductId(Long productId);
}
