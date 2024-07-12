package com.lingfenglong.spzx.controller;

import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.product.CategoryBrandDto;
import com.lingfenglong.spzx.model.entity.product.CategoryBrand;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.CategoryBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@Tag(name = "分类品牌接口")
@RestController
@RequestMapping("/admin/product/categoryBrand")
public class CategoryBrandController {

    @Resource
    private CategoryBrandService categoryBrandService;

    @Operation(summary = "分页查询")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result<PageInfo<CategoryBrand>> findPage(
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize,
            @ModelAttribute CategoryBrandDto categoryBrandDto) {
        return Result.build(categoryBrandService.findPage(pageNum, pageSize, categoryBrandDto), CommonResultCode.SUCCESS);
    }

    @Operation(summary = "保存")
    @PostMapping("/")
    public Result<?> save(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.save(categoryBrand);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "修改")
    @PutMapping("/")
    public Result<?> update(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.update(categoryBrand);
        return Result.build(null, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "删除")
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        categoryBrandService.delete(id);
        return Result.build(null, CommonResultCode.SUCCESS);
    }
}
