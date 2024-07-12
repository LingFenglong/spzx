package com.lingfenglong.spzx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.mapper.BrandMapper;
import com.lingfenglong.spzx.model.entity.product.Brand;
import com.lingfenglong.spzx.service.BrandService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Resource
    private BrandMapper brandMapper;

    public Brand findById(Long brandId) {
        return brandMapper.findById(brandId);
    }

    @Override
    public PageInfo<Brand> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(brandMapper.findAll());
    }

    @Override
    public void save(Brand brand) {
        brandMapper.save(brand);
    }

    @Override
    public void delete(Long id) {
        brandMapper.delete(id);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.update(brand);
    }

    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }

    @Override
    public List<Brand> findBrandsByCategoryId(Long id) {
        return brandMapper.findBrandsByCategoryId(id);
    }
}
