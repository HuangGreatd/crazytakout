package com.pidan.controller;

import com.pidan.common.Ressult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传和下载
 *
 * @author 黄大头
 * @date 2023年04月27日 9:01
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Ressult<String> upload(MultipartFile file) {
        return null;
    }
}
