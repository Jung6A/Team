package com.guestbook.Dto;

import com.guestbook.Entity.ProfileImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ProfileImgDto {
    private long id;
    private String imgName;
    private String imgUrl;
    private String OriginalName; //이미지 원본 이름

    private static ModelMapper mapper=new ModelMapper();

    //Entity -> DTO
    public static ProfileImgDto of(ProfileImg profileImg) {
        return mapper.map(profileImg, ProfileImgDto.class);
    }
}
