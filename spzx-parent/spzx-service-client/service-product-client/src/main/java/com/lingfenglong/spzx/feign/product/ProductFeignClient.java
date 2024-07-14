package com.lingfenglong.spzx.feign.product;

import com.lingfenglong.spzx.model.entity.product.ProductSku;
import com.lingfenglong.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", path = "/api/product/")
public interface ProductFeignClient {

    @GetMapping("/getBySkuId/{skuId}")
    Result<ProductSku> getBySkuId(@PathVariable("skuId") Long skuId);

}
