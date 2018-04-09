package com.spring.com.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@PropertySource("classpath:application.yml")
public class FileResource {
    @Value("${parameters.upload-path}")
    private String uploadPath;
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        File file=new File(uploadPath+new Date().getTime()+multipartFile.getOriginalFilename());

        multipartFile.transferTo(file);
        return new ResponseEntity<String>(file.getAbsolutePath(), HttpStatus.OK);

    }
}
