package com.guestbook.Service;

import com.guestbook.Dto.ProfileImgDto;
import com.guestbook.Entity.ProfileImg;
import com.guestbook.Repository.ProfileImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileImgService {
    @Value("${profileImagePath}") // 수정된 프로퍼티 이름
    private String imgPath;
    private final FileService fileService;
    private final ProfileImgRepository profileImgRepository;

    public void saveItemImg(ProfileImg profileImg, MultipartFile multipartFile) throws Exception {
        String originalName = multipartFile.getOriginalFilename(); // 이미지 원본 이름
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(originalName)) {
            imgName = fileService.uploadFile(imgPath, originalName, multipartFile.getBytes());
            imgUrl = "/images/" + imgName; // 웹에서 사용할 이미지 경로
        }

        profileImg.setImgUrl(imgUrl);
        profileImg.setImgName(imgName);
        profileImg.setOriginalName(originalName);
        profileImgRepository.save(profileImg);
    }

    public List<ProfileImgDto> getMainImg() {
        List<ProfileImgDto> mainImgList = new ArrayList<>();
        // 전체 상품 중 랜덤하게 4개 뽑기
        List<ProfileImg> itemList = profileImgRepository.findAll();
        // 랜덤 4개 상품의 대표이미지 가져오기
        for (ProfileImg item : itemList) {
            ProfileImgDto mainImg = new ProfileImgDto();
            ProfileImg itemImg = profileImgRepository.findByIdAndRepImgYn(item.getId(), "Y");

            if (itemImg != null) { // null 체크 추가
                mainImg.setId(item.getId()); // 상품번호
                mainImg.setImgName(item.getImgName()); // 상품명
                mainImg.setImgUrl(itemImg.getImgUrl()); // 상품 이미지
                mainImgList.add(mainImg);
            }
        }

        return mainImgList;
    }
}
