package com.dunn.model;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileImpl {

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    private MultipartFile multipartFile;
}
