package com.lingfenglong.spzx.service.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingfenglong.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    List<Category> getTopCategoryList();

    List<Category> findCategoryTree();
}
