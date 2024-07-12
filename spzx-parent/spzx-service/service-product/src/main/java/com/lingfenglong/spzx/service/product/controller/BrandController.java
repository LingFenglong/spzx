package com.lingfenglong.spzx.service.product.controller;

import com.lingfenglong.spzx.model.entity.product.Brand;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.product.service.impl.BrandServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "品牌接口")
@RestController
@RequestMapping("/api/product/brand")
public class BrandController {

    private final BrandServiceImpl brandServiceImpl;

    public BrandController(BrandServiceImpl brandServiceImpl) {
        this.brandServiceImpl = brandServiceImpl;
    }

    @GetMapping("/findAll")
    public Result<List<Brand>> findAll() {
        return Result.build(brandServiceImpl.list(), CommonResultCode.SUCCESS);
    }
}
