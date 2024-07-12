package com.lingfenglong.spzx.service.product.controller;

import com.lingfenglong.spzx.model.entity.product.Category;
import com.lingfenglong.spzx.model.entity.product.ProductSku;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.model.vo.h5.IndexVo;
import com.lingfenglong.spzx.service.product.service.CategoryService;
import com.lingfenglong.spzx.service.product.service.ProductSkuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "客户端首页")
@RestController
@RequestMapping("/api/product")
public class IndexController {

    private final CategoryService categoryService;
    private final ProductSkuService productSkuService;

    public IndexController(CategoryService categoryService, ProductSkuService productSkuService) {
        this.categoryService = categoryService;
        this.productSkuService = productSkuService;
    }

    @Operation(summary = "分类和畅销商品")
    @GetMapping("/index")
    public Result<IndexVo> index() {
        IndexVo indexVo = new IndexVo();

        List<Category> categoryList = categoryService.getTopCategoryList();
        List<ProductSku> productSkuList = productSkuService.getHotProductList();

        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, CommonResultCode.SUCCESS);
    }
}
