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
        }

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
