package com.guestbook.Service;


import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {
    public String uploadFile(String uploadPath,
                             String originalName, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID(); // 이미지 이름 중복을 방지하기위한 랜덤 문자생성
        // 업로드 파일의 확장자 ->  .jpg
        String ext = originalName.substring(originalName.lastIndexOf("."));
        // 실제 업로드해서 저장될 이름
        String saveName = uuid.toString() + ext; //새로만들어진 이름에 확장자 붙이기
        String fileUploadUrl = uploadPath+"/"+saveName;// 업로드경로와 파일명
        FileOutputStream fos = new FileOutputStream(fileUploadUrl);
        fos.write(fileData);
        fos.close();
        return saveName;
    }
}
/*
       try( FileOutputStream fos = new FileOutputStream(fileUploadUrl) )
       {
            fos.write(fileData);
       }catch(){

       }

 */