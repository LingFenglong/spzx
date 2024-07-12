package com.lingfenglong.spzx.service.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.h5.ProductSkuDto;
import com.lingfenglong.spzx.model.entity.product.Product;
import com.lingfenglong.spzx.model.entity.product.ProductSku;
import com.lingfenglong.spzx.service.product.mapper.ProductMapper;
import com.lingfenglong.spzx.service.product.mapper.ProductSkuMapper;
import com.lingfenglong.spzx.service.product.service.ProductSkuService;
import com.lingfenglong.spzx.service.product.utils.ProductSkuOrderStrategy;
import com.lingfenglong.spzx.service.product.utils.ProductSkuOrderStrategyContainer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ProductSkuServiceImpl
        extends ServiceImpl<ProductSkuMapper, ProductSku>
        implements ProductSkuService {

    private final ProductSkuOrderStrategyContainer productSkuOrderStrategyContainer;
    private final ProductMapper productMapper;

    public ProductSkuServiceImpl(ProductSkuOrderStrategyContainer productSkuOrderStrategyContainer, ProductMapper productMapper) {
        this.productSkuOrderStrategyContainer = productSkuOrderStrategyContainer;
        this.productMapper = productMapper;
    }

    @Override
    @Cacheable(cacheNames = "hotProductList")
    public List<ProductSku> getHotProductList() {
        return baseMapper.getHotProductList();
    }

    @Override
    public PageInfo<ProductSku> getProductSkuPage(int pageNum, int pageSize, ProductSkuDto productSkuDto) {
        // 查询所有的商品id
        LambdaQueryWrapper<Product> productIdsWrapper = new LambdaQueryWrapper<Product>()
                .select(Product::getId)
                .eq(productSkuDto.getBrandId() != null && productSkuDto.getBrandId() > 0, Product::getBrandId, productSkuDto.getBrandId())
                .eq(productSkuDto.getCategory1Id() != null && productSkuDto.getCategory1Id() > 0, Product::getCategory1Id, productSkuDto.getCategory1Id())
                .eq(productSkuDto.getCategory2Id() != null && productSkuDto.getCategory2Id() > 0, Product::getCategory2Id, productSkuDto.getCategory2Id())
                .eq(productSkuDto.getCategory3Id() != null && productSkuDto.getCategory3Id() > 0, Product::getCategory3Id, productSkuDto.getCategory3Id())
                .like(productSkuDto.getKeyword() != null, Product::getName, productSkuDto.getKeyword())
                .eq(Product::getStatus, 1);

        List<Long> ids = productMapper.selectObjs(productIdsWrapper);

        if (CollectionUtils.isEmpty(ids)) {
            return new PageInfo<>();
        }

        ProductSkuOrderStrategy productSkuOrderStrategy =
                productSkuOrderStrategyContainer.getStrategy(productSkuDto.getOrder());

        // 查询Sku
        LambdaQueryWrapper<ProductSku> productSkusWrapper = new LambdaQueryWrapper<ProductSku>()
                .in(ProductSku::getProductId, ids)
                .orderBy(
                        productSkuDto.getOrder() != null,
                        productSkuOrderStrategy.isAsc(),
                        productSkuOrderStrategy.getColumns()
                );

        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(baseMapper.selectList(productSkusWrapper));
    }

    @Override
    public List<ProductSku> getAllByProductId(Long productId) {
        LambdaQueryWrapper<ProductSku> wrapper = new LambdaQueryWrapper<ProductSku>()
                .eq(ProductSku::getProductId, productId);
        return baseMapper.selectList(wrapper);
    }
}
