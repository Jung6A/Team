package com.guestbook.Service;

import com.guestbook.Entity.MemberImg;
import com.guestbook.Repository.MemberImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberImgService {
    @Value("${uploadPath}")
    private String uploadPath;
    private final FileService fileService;
    private final MemberImgRepository itemImgRepository;

    public void saveItemImg(MemberImg memberImg, MultipartFile multipartFile) throws Exception {
        String originalName=multipartFile.getOriginalFilename(); //이미지 원본 이름
        String imgName="";
        String imgUrl="";
        //파일 업로드
        if(!StringUtils.isEmpty(originalName)){
            imgName=fileService.uploadFile(uploadPath, originalName, multipartFile.getBytes());
            imgUrl="/images/"+imgName; //웹에 사용할 이미지 경로
        }
        memberImg.setImgUrl(imgUrl);
        memberImg.setImgName(imgName);
        memberImg.setOriginalName(originalName);
        itemImgRepository.save(memberImg);
    }
}
