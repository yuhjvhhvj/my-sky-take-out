package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("admin/common")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;
    /**
     *  文件上传
     */
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        log.info("文件上传");
//        String objectName=null;

        try {
            String originalFilename= file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")) ;
            String objectName = UUID.randomUUID()+extension;
            String fileName = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(fileName);
        } catch (IOException e) {
            log.error("文件上传失败"+e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
