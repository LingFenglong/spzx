package com.lingfenglong.spzx.mapper;

import com.lingfenglong.spzx.model.entity.product.Category;
import com.lingfenglong.spzx.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findCategoryListByParentId(@Param("id") Long id);

    Integer countCategoryListByParentId(@Param("id") Long id);

    List<CategoryExcelVo> findAll();

    void saveBatch(ArrayList<CategoryExcelVo> categoryList);

    List<Category> findCategoriesIn(@Param("ids") List<Long> categoryId);
}
