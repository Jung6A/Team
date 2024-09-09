package com.guestbook.Service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class FileService {
    private static final Logger logger = Logger.getLogger(FileService.class.getName());

    public String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {
        // 디렉토리 확인 및 생성
        Path filePath = Paths.get(uploadPath);
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath);
        }

        // 파일 확장자 추출
        String ext = originalName.substring(originalName.lastIndexOf("."));
        // UUID로 기본 이름 생성
        String fileName = UUID.randomUUID().toString();
        // 실제 업로드할 파일 이름 (중복 처리 포함)
        String saveName = getUniqueFileName(uploadPath, fileName, ext);

        // 파일 저장 경로
        String fileUploadUrl = uploadPath + "/" + saveName;

        // 파일 저장
        try (FileOutputStream fos = new FileOutputStream(fileUploadUrl)) {
            fos.write(fileData);
        } catch (Exception e) {
            logger.severe("파일 저장 중 오류 발생: " + e.getMessage());
            throw new Exception("파일 저장 실패", e);
        }

        return saveName;
    }

    private String getUniqueFileName(String uploadPath, String fileName, String fileExtension) {
        int count = 1;
        String newFileName = fileName + fileExtension;
        File file = new File(uploadPath, newFileName);

        while (file.exists()) {
            newFileName = fileName + "(" + count + ")" + fileExtension;
            file = new File(uploadPath, newFileName);
            count++;
        }

        return newFileName;
    }
}
