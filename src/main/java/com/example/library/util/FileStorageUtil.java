package com.example.library.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileStorageUtil {
@Value("${default.img}")
    String defaultImg;
    public String saveFile(MultipartFile file, String uploadPath) throws IOException {
        if ((file != null) && (!file.isEmpty())) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String filename = file.getOriginalFilename();
            if (filename.equals("blob")) {
                filename = ".png";
            }
            String resultFileName = uploadPath + File.separator + UUID.randomUUID().toString() + filename;
            file.transferTo(Paths.get(resultFileName));
            return resultFileName;
        }
        return defaultImg;
    }


}