package com.lingfenglong.spzx.controller;

import com.lingfenglong.spzx.model.entity.base.ProductUnit;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.impl.ProductUnitServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "商品单位管理")
@RestController
@RequestMapping("/admin/product/productUnit")
public class ProductUnitController {

    private final ProductUnitServiceImpl productUnitServiceImpl;

    public ProductUnitController(ProductUnitServiceImpl productUnitServiceImpl) {
        this.productUnitServiceImpl = productUnitServiceImpl;
    }

    @Operation(summary = "查询所有单位")
    @GetMapping("/findAll")
    public Result<List<ProductUnit>> findAll() {
        return Result.build(
                productUnitServiceImpl.list(),
                CommonResultCode.SUCCESS);
    }
}
