package com.guestbook.Dto;

import com.guestbook.Entity.MemberImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class MemberImgDto {
    private long id;

    private String imgName; //이미지 이름
    private String imgUrl; //이미지 Url
    private String OriginalName; //이미지 원본 이름

    private static ModelMapper mapper=new ModelMapper();

    //Entity -> DTO
    public static MemberImgDto of(MemberImg memberImg) {
        return mapper.map(memberImg, MemberImgDto.class);
    }
}
