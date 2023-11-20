package com.lingfenglong.spzx.controller;

import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件上传接口")
@RestController
@RequestMapping("/admin/system/fileUpload")
public class FileUploadController {
    private FileUploadService fileUploadService;

    @Operation(summary = "用户头像上传接口")
    @PostMapping("/avatar")
    public Result<String> avatar(MultipartFile file) {
        String url =  fileUploadService.save(file);
        return Result.build(url, CommonResultCode.SUCCESS);
    }

    @Autowired
    public void setFileUploadService(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }
}
