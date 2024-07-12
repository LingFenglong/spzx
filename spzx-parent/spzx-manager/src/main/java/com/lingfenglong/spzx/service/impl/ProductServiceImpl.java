package com.lingfenglong.spzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.enums.ProductAuditStatus;
import com.lingfenglong.spzx.mapper.CategoryMapper;
import com.lingfenglong.spzx.mapperplus.ProductServiceMapper;
import com.lingfenglong.spzx.model.dto.product.ProductDto;
import com.lingfenglong.spzx.model.entity.product.*;
import com.lingfenglong.spzx.service.ProductService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductServiceMapper, Product> implements ProductService {

    private final CategoryServiceImpl categoryServiceImpl;
    private final BrandServiceImpl brandServiceImpl;
    private final ProductSkuServiceImpl productSkuServiceImpl;
    private final ProductDetailsServiceImpl productDetailsServiceImpl;

    public ProductServiceImpl(CategoryServiceImpl categoryServiceImpl, CategoryMapper categoryMapper, BrandServiceImpl brandServiceImpl, ProductSkuServiceImpl productSkuServiceImpl, ProductDetailsServiceImpl productDetailsServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
        this.brandServiceImpl = brandServiceImpl;
        this.productSkuServiceImpl = productSkuServiceImpl;
        this.productDetailsServiceImpl = productDetailsServiceImpl;
    }

    @Override
    public Page<Product> pageByProductDto(Long pageNum, Long pageSize, ProductDto productDto) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>(Product.class)
                .eq(productDto.getBrandId() != null && productDto.getBrandId() > 0,
                        Product::getBrandId, productDto.getBrandId())

                .eq(productDto.getCategory1Id() != null && productDto.getCategory1Id() > 0,
                        Product::getCategory1Id, productDto.getCategory1Id())

                .eq(productDto.getCategory2Id() != null && productDto.getCategory2Id() > 0,
                        Product::getCategory2Id, productDto.getCategory2Id())

                .eq(productDto.getCategory3Id() != null && productDto.getCategory3Id() > 0,
                        Product::getCategory3Id, productDto.getCategory3Id());

        Page<Product> page = page(new Page<>(pageNum, pageSize), wrapper);

        // 查询品牌名
        page.getRecords().forEach(this::find);

        return page;
    }

    @Override
    public boolean removeById(Serializable id) {
        productSkuServiceImpl.remove(
                new LambdaQueryWrapper<ProductSku>()
                        .eq(ProductSku::getProductId, id)
        );

        productDetailsServiceImpl.remove(
                new LambdaQueryWrapper<ProductDetails>()
                        .eq(ProductDetails::getProductId, id)
        );

        return super.removeById(id);
    }

    @Override
    public boolean saveOrUpdate(Product product) {
        // 保存 product
        super.saveOrUpdate(product);

        // 保存 product sku
        AtomicInteger productSkuIndex = new AtomicInteger(0);
        List<ProductSku> productSkus = product.getProductSkuList()
                .stream()
                .peek(productSku -> {
                    // 商品Id
                    productSku.setProductId(product.getId());

                    // 商品编号
                    productSku.setSkuCode(product.getId() + "_" + productSkuIndex.get());

                    // sku name
                    productSku.setSkuName(product.getName() + " " + productSku.getSkuSpec());

                    // 销量
                    productSku.setSaleNum(0);
                    productSku.setStatus(0);
                    productSkuIndex.incrementAndGet();
                })
                .toList();
        productSkuServiceImpl.saveOrUpdateBatch(productSkus);

        // 保存 product details
        ProductDetails productDetails = productDetailsServiceImpl.getByProductId(product.getId());

        Optional.ofNullable(productDetails)
                .ifPresentOrElse(pd -> {
                    pd.setImageUrls(product.getDetailsImageUrls());
                    productDetailsServiceImpl.updateById(pd);
                }, () -> {
                    ProductDetails pd = ProductDetails.builder()
                            .productId(product.getId())
                            .imageUrls(product.getDetailsImageUrls())
                            .build();
                    productDetailsServiceImpl.save(pd);
                });
        return true;
    }

    @Override
    public Product findById(Long id) {
        Product product = baseMapper.selectById(id);
        find(product);

        return product;
    }

    @Override
    public void updateAudit(Long productId, Integer auditStatus) {
        baseMapper.update(new LambdaUpdateWrapper<Product>()
                .eq(Product::getId, productId)
                .set(Product::getAuditStatus, auditStatus)
                .set(Product::getAuditMessage, ProductAuditStatus.getProductAuditStatus(auditStatus).getMessage()));
    }

    @Override
    public void updateProductStatus(Long productId, Integer status) {
        baseMapper.update(new LambdaUpdateWrapper<Product>()
                .eq(Product::getId, productId)
                .set(Product::getStatus, status));
    }

    /**
     * 查询其他表中的信息，保存到 product
     */
    private void find(Product product) {
        // 查询品牌名
        Brand brand = brandServiceImpl.findById(product.getBrandId());
        product.setBrandName(brand == null ? "" : brand.getName());

        // 查询分类名字
        List<Category> categories = categoryServiceImpl.findCategoriesIn(List.of(
                product.getCategory1Id(),
                product.getCategory2Id(),
                product.getCategory3Id()));

        product.setCategory1Id(categories.get(0).getId());
        product.setCategory2Id(categories.get(1).getId());
        product.setCategory3Id(categories.get(2).getId());

        // 查询商品 sku
        List<ProductSku> skus = productSkuServiceImpl.list(
                new LambdaQueryWrapper<>(ProductSku.class).
                        eq(ProductSku::getProductId, product.getId()));
        product.setProductSkuList(skus);

        // 查询商品 details
        ProductDetails details = productDetailsServiceImpl.getOne(
                new LambdaQueryWrapper<>(ProductDetails.class).
                        eq(ProductDetails::getProductId, product.getId()));
        product.setDetailsImageUrls(details == null ? "" : details.getImageUrls());
    }
}