package com.lingfenglong.spzx.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String save(MultipartFile file);
}
