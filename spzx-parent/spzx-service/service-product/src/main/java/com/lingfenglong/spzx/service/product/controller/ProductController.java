package com.lingfenglong.spzx.service.product.controller;

import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.h5.ProductSkuDto;
import com.lingfenglong.spzx.model.entity.product.ProductSku;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.model.vo.h5.ProductItemVo;
import com.lingfenglong.spzx.service.product.service.impl.ProductServiceImpl;
import com.lingfenglong.spzx.service.product.service.impl.ProductSkuServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "产品接口")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductSkuServiceImpl productSkuServiceImpl;
    private final ProductServiceImpl productServiceImpl;

    public ProductController(ProductSkuServiceImpl productSkuServiceImpl, ProductServiceImpl productServiceImpl) {
        this.productSkuServiceImpl = productSkuServiceImpl;
        this.productServiceImpl = productServiceImpl;
    }

    @Operation(summary = "查询某一商品的详情")
    @GetMapping("/item/{skuId}")
    public Result<ProductItemVo> getItem(@PathVariable("skuId") Long skuId) {
        ProductItemVo productItemVo = productServiceImpl.getItem(skuId);
        return Result.build(productItemVo, CommonResultCode.SUCCESS);
    }
    
    @GetMapping("/{pageNum}/{pageSize}")
    public Result<PageInfo<ProductSku>> getProductSkuPage(
            @PathVariable("pageNum") int pageNum,
            @PathVariable("pageSize") int pageSize,
            ProductSkuDto productSkuDto) {

        return Result.build(
                productSkuServiceImpl.getProductSkuPage(pageNum, pageSize, productSkuDto),
                CommonResultCode.SUCCESS);
    }
    
    @GetMapping("/getBySkuId/{skuId}")
    public Result<ProductSku> getBySkuId(@PathVariable("skuId") Long skuId) {
        ProductSku productSku = productSkuServiceImpl.getBySkuId(skuId);
        return Result.build(productSku, CommonResultCode.SUCCESS);
    }
}
