package com.guestbook.Dto;

import com.guestbook.Entity.GuestbookContent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GuestbookContentDto {

    private Long id;                    // 댓글의 ID
    private String writer;              // 댓글 작성자
    private String content;             // 댓글 내용
    private String guestbookId;         // 방명록 ID
    private LocalDateTime createdDate;  // 댓글 작성 시간
    private String profileImageName;    // 프로필 이미지 이름 추가

    // GuestbookContent 엔티티를 DTO로 변환하는 메서드
    public static GuestbookContentDto of(GuestbookContent guestbookContent) {
        GuestbookContentDto dto = new GuestbookContentDto();
        dto.setId(guestbookContent.getId());
        dto.setWriter(guestbookContent.getWriter());
        dto.setContent(guestbookContent.getContent());
        dto.setGuestbookId(guestbookContent.getGuestbook().getGuestbookId());
        dto.setCreatedDate(guestbookContent.getCreatedDate());
        return dto;
    }

    public void setProfileImageName(String profileImageName) {
        this.profileImageName = profileImageName;
    }
}
