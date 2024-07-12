package com.lingfenglong.spzx.service;

import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.entity.product.Brand;

import java.util.List;

public interface BrandService {
    PageInfo<Brand> findPage(Integer pageSize, Integer pageNum);

    void save(Brand brand);

    void delete(Long id);

    void update(Brand brand);

    List<Brand> findAll();

    List<Brand> findBrandsByCategoryId(Long id);
}
