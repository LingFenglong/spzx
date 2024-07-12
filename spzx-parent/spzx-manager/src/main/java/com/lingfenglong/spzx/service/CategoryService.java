package com.lingfenglong.spzx.service;

import com.lingfenglong.spzx.model.entity.product.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<Category> findCategoryListByParentId(Long id);

    void exportCategoryList();

    void importCategoryList(MultipartFile file);
}
