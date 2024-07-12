package com.lingfenglong.spzx.controller;

import com.lingfenglong.spzx.model.entity.product.Category;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "分类管理")
@RestController
@RequestMapping("/admin/system/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "查询分类列表")
    @GetMapping("/{id}")
    public Result<List<Category>> findCategoryList(@PathVariable("id") Long id) {
        List<Category> categoryList = categoryService.findCategoryListByParentId(id);
        return Result.build(categoryList, CommonResultCode.SUCCESS);
    }

    @Operation(summary = "导出数据")
    @GetMapping("/export")
    public void exportCategoryList() {
        categoryService.exportCategoryList();
    }

    @Operation(summary = "导入数据")
    @PostMapping("/import")
    public Result<?> importCategoryList(@ModelAttribute("file") MultipartFile file) {
        categoryService.importCategoryList(file);
        return Result.build(null, CommonResultCode.SUCCESS);
    }
}
