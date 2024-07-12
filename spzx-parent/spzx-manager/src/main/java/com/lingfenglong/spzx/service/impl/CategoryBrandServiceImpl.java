package com.lingfenglong.spzx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.mapper.CategoryBrandMapper;
import com.lingfenglong.spzx.model.dto.product.CategoryBrandDto;
import com.lingfenglong.spzx.model.entity.product.CategoryBrand;
import com.lingfenglong.spzx.service.CategoryBrandService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {
    @Resource
    private CategoryBrandMapper categoryBrandMapper;


    @Override
    public PageInfo<CategoryBrand> findPage(Integer pageNum, Integer pageSize, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(categoryBrandMapper.findPageByCategoryBrandDto(categoryBrandDto));
    }

    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.save(categoryBrand);
    }

    @Override
    public void delete(Long id) {
        categoryBrandMapper.delete(id);
    }

    @Override
    public void update(CategoryBrand categoryBrand) {
        categoryBrandMapper.update(categoryBrand);
    }
}
