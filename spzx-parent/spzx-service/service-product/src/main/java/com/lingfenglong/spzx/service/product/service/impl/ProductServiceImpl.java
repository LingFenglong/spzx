package com.lingfenglong.spzx.service.product.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.model.entity.product.Product;
import com.lingfenglong.spzx.model.entity.product.ProductDetails;
import com.lingfenglong.spzx.model.entity.product.ProductSku;
import com.lingfenglong.spzx.model.vo.h5.ProductItemVo;
import com.lingfenglong.spzx.service.product.mapper.ProductMapper;
import com.lingfenglong.spzx.service.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductServiceImpl
        extends ServiceImpl<ProductMapper, Product>
        implements ProductService {

    private final ProductSkuServiceImpl productSkuServiceImpl;
    private final ProductDetailsServiceImpl productDetailsServiceImpl;

    public ProductServiceImpl(ProductSkuServiceImpl productSkuServiceImpl, ProductDetailsServiceImpl productDetailsServiceImpl, ProductSpecServiceImpl productSpecServiceImpl) {
        this.productSkuServiceImpl = productSkuServiceImpl;
        this.productDetailsServiceImpl = productDetailsServiceImpl;
    }

    @Override
    public ProductItemVo getItem(Long skuId) {
        ProductSku productSku = productSkuServiceImpl.getById(skuId);
        Long productId = productSku.getProductId();

        Product product = baseMapper.selectById(productId);
        if (product.getStatus() != 1) {
            throw new RuntimeException("商品未上架！");
        }

        ProductDetails productDetails = productDetailsServiceImpl.getByProductId(productId);
        List<ProductSku> productSkus = productSkuServiceImpl.getAllByProductId(productId);

        HashMap<String, Object> skuSpecValueMap =
                productSkus.stream()
                        .collect(
                                HashMap::new,
                                (map, sku) -> {
                                    map.put(sku.getSkuSpec(), sku.getId());
                                },
                                HashMap::putAll
                        );

        return ProductItemVo.builder()
                .product(product)
                .productSku(productSku)
                .sliderUrlList(Arrays.asList(StringUtils.commaDelimitedListToStringArray(product.getSliderUrls())))
                .detailsImageUrlList(Arrays.asList(StringUtils.commaDelimitedListToStringArray(productDetails.getImageUrls())))
                .specValueList(JSONArray.parse(product.getSpecValue()))
                .skuSpecValueMap(skuSpecValueMap)
                .build();
    }
}
