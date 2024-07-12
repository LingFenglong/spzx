package com.lingfenglong.spzx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.entity.product.ProductSpec;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.impl.ProductSpecServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "产品规格")
@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {
    @Autowired
    private ProductSpecServiceImpl productSpecService;

    @Operation(summary = "添加")
    @PostMapping("/")
    public Result<?> save(@RequestBody ProductSpec productSpec) {
        productSpecService.save(productSpec);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "删除")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        productSpecService.removeById(id);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "修改")
    @PutMapping("/")
    public Result<?> update(@RequestBody ProductSpec productSpec) {
        productSpecService.updateById(productSpec);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "分页查询")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result<PageInfo<ProductSpec>> findPage(
            @PathVariable Integer pageNum,
            @PathVariable Integer pageSize
    ) {

        return Result.build(
                new PageInfo<>(productSpecService.page(new Page<>(pageNum, pageSize)).getRecords()),
                CommonResultCode.SUCCESS
        );
    }

    @Operation(summary = "查询所有商品规格")
    @GetMapping("/findAll")
    public Result<List<ProductSpec>> findAll() {
        return Result.build(
                productSpecService.list(),
                CommonResultCode.SUCCESS
        );
    }

}
