package com.lingfenglong.spzx.controller;

import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.entity.product.Brand;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "品牌接口")
@RestController
@RequestMapping("/admin/product/brand")
public class BrandController {
    @Resource
    private BrandService brandService;

    @Operation(summary = "分页查询")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result<PageInfo<Brand>> findPage(
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize) {
        PageInfo<Brand> pageInfo = brandService.findPage(pageNum, pageSize);
        return Result.build(pageInfo, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "添加")
    @PostMapping("/")
    public Result<?> save(@RequestBody Brand brand) {
        brandService.save(brand);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "逻辑删除")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable("id") Long id) {
        brandService.delete(id);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "更新")
    @PutMapping("/")
    public Result<?> update(@RequestBody Brand brand) {
        brandService.update(brand);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "查询所有品牌")
    @GetMapping("/")
    public Result<List<Brand>> findAll() {
        return Result.build(brandService.findAll(), CommonResultCode.SUCCESS);
    }

    @Operation(summary = "根据分类查询对应的品牌")
    @GetMapping("/findBrandsByCategoryId/{id}")
    public Result<List<Brand>> findByCategoryId(@PathVariable("id") Long id) {
        return Result.build(
                brandService.findBrandsByCategoryId(id),
                CommonResultCode.SUCCESS
        );
    }
}
