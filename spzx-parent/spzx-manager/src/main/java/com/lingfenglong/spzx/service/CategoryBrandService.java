package com.lingfenglong.spzx.service;

import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.dto.product.CategoryBrandDto;
import com.lingfenglong.spzx.model.entity.product.CategoryBrand;

public interface CategoryBrandService {
    PageInfo<CategoryBrand> findPage(Integer pageNum, Integer pageSize, CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void delete(Long id);

    void update(CategoryBrand categoryBrand);
}
