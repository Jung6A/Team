package com.guestbook.Dto;

import com.guestbook.Entity.GuestbookContent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GuestbookContentDto {

    private Long id;
    private String writer;
    private String content;
    private String guestbookId;
    private LocalDateTime createdDate;
    private String profileImageName; // 프로필 이미지 이름 추가

    public static GuestbookContentDto of(GuestbookContent guestbookContent) {
        GuestbookContentDto dto = new GuestbookContentDto();
        dto.setId(guestbookContent.getId());
        dto.setWriter(guestbookContent.getWriter());
        dto.setContent(guestbookContent.getContent());
        dto.setGuestbookId(guestbookContent.getGuestbook().getGuestbookId());
        dto.setCreatedDate(guestbookContent.getCreatedDate());
        // 프로필 이미지 이름은 Member 객체에서 가져와야 함
        dto.setProfileImageName(guestbookContent.getGuestbook().getMember().getProfileImageName());
        return dto;
    }
}
