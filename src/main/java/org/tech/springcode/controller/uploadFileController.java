package org.tech.springcode.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.tech.springcode.utils.AliCloudUploadUtil;
import org.tech.springcode.utils.Result;

@RestController
@RequestMapping("/upload")
public class uploadFileController {

    @PostMapping("/uploadFile")
    public Result<String> uploadFile(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String firstLetter = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = firstLetter + suffix;
        String url = AliCloudUploadUtil.uploadFile(fileName, file.getInputStream());
        return Result.success(url);
    }
}
