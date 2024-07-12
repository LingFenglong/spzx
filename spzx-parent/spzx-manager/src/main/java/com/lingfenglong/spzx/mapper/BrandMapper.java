package com.lingfenglong.spzx.mapper;

import com.github.pagehelper.PageInfo;
import com.lingfenglong.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<Brand> findAll();

    void save(Brand brand);

    void delete(@Param("id") Long id);

    void update(Brand brand);

    Brand findById(@Param("id") Long brandId);

    List<Brand> findBrandsByCategoryId(@Param("id") Long id);
}
