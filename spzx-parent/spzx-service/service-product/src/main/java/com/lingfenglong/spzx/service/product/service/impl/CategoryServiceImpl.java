package com.lingfenglong.spzx.service.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.spzx.model.entity.product.Category;
import com.lingfenglong.spzx.service.product.mapper.CategoryMapper;
import com.lingfenglong.spzx.service.product.service.CategoryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl
    extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {

    @Override
    @Cacheable(cacheNames = "topCategoryList")
    public List<Category> getTopCategoryList() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<Category>()
                .eq(Category::getParentId, 0);
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Cacheable(cacheNames = "categoryTree")
    public List<Category> findCategoryTree() {
        List<Category> categories = baseMapper.selectList(null);
        return findCategoryTreeHelper(categories, 0L);
    }

    private List<Category> findCategoryTreeHelper(List<Category> categories, Long parentId) {
        if (categories == null) {
            return null;
        }

        return categories.stream()
                .filter(category -> category.getParentId().equals(parentId))
                .peek(category -> Optional
                        .ofNullable(findCategoryTreeHelper(categories, category.getId()))
                        .filter(childrenCategories -> !childrenCategories.isEmpty())
                        .ifPresent(childrenCategories -> {
                            category.setHasChildren(true);
                            category.setChildren(childrenCategories);
                        }))
                .collect(Collectors.toList());
    }
}
