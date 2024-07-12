package com.lingfenglong.spzx.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.google.common.base.Charsets;
import com.lingfenglong.spzx.common.exception.CommonGlobalRuntimeException;
import com.lingfenglong.spzx.mapper.CategoryMapper;
import com.lingfenglong.spzx.model.entity.product.Category;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.product.CategoryExcelVo;
import com.lingfenglong.spzx.service.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryMapper categoryMapper;

    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> findCategoryListByParentId(Long id) {
        return categoryMapper.findCategoryListByParentId(id)
                .stream()
                .peek(category -> category.setHasChildren(categoryMapper.countCategoryListByParentId(category.getId()) > 0))
                .collect(Collectors.toList());
    }

    @Override
    public void exportCategoryList() {
        List<CategoryExcelVo> categoryExcelVoList = categoryMapper.findAll();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        // 设置响应结果类型
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader(
                "Content-disposition",
                "attachment;fileName=" + URLEncoder.encode("产品分类", Charsets.UTF_8) + ".xlsx"
        );

        try {
            EasyExcel
                    .write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet()
                    .doWrite(categoryExcelVoList);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonGlobalRuntimeException(CommonResultCode.DATA_ERROR);
        }
    }

    @Override
    public void importCategoryList(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, new ReadListener<CategoryExcelVo>() {
                private static final int MAX_CACHE = 100;
                private final ArrayList<CategoryExcelVo> cacheList = ListUtils.newArrayListWithExpectedSize(100);

                @Override
                public void invoke(CategoryExcelVo data, AnalysisContext context) {
                    cacheList.add(data);
                    if (cacheList.size() == MAX_CACHE) {
                        // 写入到数据库
                        categoryMapper.saveBatch(cacheList);
                        cacheList.clear();
                    }
                }
                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    categoryMapper.saveBatch(cacheList);
                }
            })
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonGlobalRuntimeException(CommonResultCode.DATA_ERROR);
        }
    }

    public List<Category> findCategoriesIn(List<Long> categoryId) {
        return categoryMapper.findCategoriesIn(categoryId);
    }
}
