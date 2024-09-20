package com.guestbook.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileService {
    //파일 업로드시 저장 메서드
    public String uploadFile(String uploadPath, String originalName, byte[] fileData) throws IOException {
        Path path = Paths.get(uploadPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        String ext = originalName.substring(originalName.lastIndexOf("."));
        String fileName = originalName.substring(0, originalName.lastIndexOf("."));
        String saveName = fileName + ext;

        // 파일 이름 중복 체크 및 수정
        int count = 1;
        while (Files.exists(path.resolve(saveName))) {
            saveName = fileName + "(" + count + ")" + ext;
            count++;
        } //중복되는 이름의 파일이 존재할 경우 (1) (2) 등 붙여서 저장

        Path filePath = path.resolve(saveName);

        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(fileData);
        } catch (IOException e) {
            log.error("파일 저장 중 오류 발생: {}", e.getMessage());
            throw new IOException("파일 저장 실패", e);
        }

        return saveName;
    }
}
