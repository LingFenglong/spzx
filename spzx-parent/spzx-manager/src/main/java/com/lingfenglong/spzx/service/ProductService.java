package com.lingfenglong.spzx.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lingfenglong.spzx.model.dto.product.ProductDto;
import com.lingfenglong.spzx.model.entity.product.Product;


public interface ProductService extends IService<Product> {
    Page<Product> pageByProductDto(Long pageNum, Long pageSize, ProductDto productDto);

    Product findById(Long id);

    void updateAudit(Long productId, Integer auditStatus);

    void updateProductStatus(Long productId, Integer status);
}
