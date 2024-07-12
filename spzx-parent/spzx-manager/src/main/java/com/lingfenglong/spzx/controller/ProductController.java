package com.lingfenglong.spzx.controller;

import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.product.ProductDto;
import com.lingfenglong.spzx.model.entity.product.Product;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "商品管理", description = "商品管理")
@RestController
@RequestMapping("/admin/product/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "分页查询商品")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result<PageInfo<Product>> findPage(
            @PathVariable Long pageNum,
            @PathVariable Long pageSize,
            @ModelAttribute ProductDto productDto) {

        return Result.build(
                new PageInfo<>(productService.pageByProductDto(pageNum, pageSize, productDto).getRecords()),
                CommonResultCode.SUCCESS
        );
    }

    @Operation(summary = "保存商品")
    @PostMapping("/save")
    public Result<?> saveProduct(@RequestBody Product product) {
        productService.saveOrUpdate(product);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "根据Id查询商品信息")
    @GetMapping("/getById/{id}")
    public Result<Product> findById(@PathVariable("id") Long id) {
        return Result.build(productService.findById(id), CommonResultCode.SUCCESS);
    }

    @Operation(summary = "更新商品")
    @PutMapping("/updateById")
    public Result<?> updateById(@RequestBody Product product) {
        productService.saveOrUpdate(product);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "删除商品")
    @DeleteMapping("/deleteById/{id}")
    public Result<?> deleteById(@PathVariable("id") Long id) {
        productService.removeById(id);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "商品审核")
    @GetMapping("/updateAuditStatus/{productId}/{auditStatus}")
    public Result<?> updateAuditStatus(
            @PathVariable("productId") Long productId,
            @PathVariable("auditStatus") Integer auditStatus) {
        productService.updateAudit(productId, auditStatus);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "商品上下架")
    @GetMapping("/updateStatus/{productId}/{status}")
    public Result<?> updateProductStatus(
            @PathVariable("productId") Long productId,
            @PathVariable("status") Integer status) {
        productService.updateProductStatus(productId, status);
        return Result.build(null, CommonResultCode.SUCCESS);
    }
}
