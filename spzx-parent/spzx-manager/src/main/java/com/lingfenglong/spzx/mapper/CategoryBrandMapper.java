package com.lingfenglong.spzx.mapper;

import com.lingfenglong.spzx.model.dto.product.CategoryBrandDto;
import com.lingfenglong.spzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryBrandMapper {
    List<CategoryBrand> findAll();

    List<CategoryBrand> findPageByCategoryBrandDto(CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void delete(Long id);

    void update(CategoryBrand categoryBrand);
}
