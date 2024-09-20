package com.guestbook.Dto;

import com.guestbook.Entity.MemberImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class MemberImgDto {
    private long id;  // 이미지 ID

    private String imgName;  // 이미지 이름
    private String imgUrl;  // 이미지 URL
    private String originalName;  // 이미지 원본 이름 (오타 수정)

    private static ModelMapper mapper = new ModelMapper();  // ModelMapper 인스턴스

    // Entity -> DTO 변환 메서드
    public static MemberImgDto of(MemberImg memberImg) {
        return mapper.map(memberImg, MemberImgDto.class);  // ModelMapper를 사용해 MemberImg 엔티티를 MemberImgDto로 변환
    }
}
