package com.guestbook.Service;

import com.guestbook.Entity.ProfileImg;
import com.guestbook.Repository.ProfileImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileImgService {

    @Value("${itemImgPath}")
    private String imgPath;
    private final FileService fileService;
    private final ProfileImgRepository itemImgRepository;

    public void saveItemImg(ProfileImg profileImg, MultipartFile multipartFile) throws Exception {
        String originalName=multipartFile.getOriginalFilename(); //이미지 원본 이름
        String imgName="";
        String imgUrl="";
        //파일 업로드
        if(!StringUtils.isEmpty(originalName)){
            imgName=fileService.uploadFile(imgPath, originalName, multipartFile.getBytes());
            imgUrl="/images/"+imgName; //웹에 사용할 이미지 경로
        }
        profileImg.setImgUrl(imgUrl);
        profileImg.setImgName(imgName);
        profileImg.setOriginalName(originalName);
        itemImgRepository.save(profileImg);
    }

}
