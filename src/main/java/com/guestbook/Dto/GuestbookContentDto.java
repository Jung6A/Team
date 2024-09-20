package com.guestbook.Dto;

import com.guestbook.Entity.GuestbookContent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GuestbookContentDto {

    private Long id;                    // 방명록 내용의 ID
    private String writer;              // 작성자 ID
    private String content;             // 방명록 내용
    private String guestbookId;         // 방명록 ID (참조하는 방명록의 ID)
    private LocalDateTime createdDate;  // 작성 일자
    private String profileImageName;    // 작성자의 프로필 이미지 이름

    // GuestbookContent 엔티티를 DTO로 변환하는 메서드
    public static GuestbookContentDto of(GuestbookContent guestbookContent) {
        GuestbookContentDto dto = new GuestbookContentDto();
        dto.setId(guestbookContent.getId()); // 방명록 내용 ID 설정
        dto.setWriter(guestbookContent.getWriter()); // 작성자 ID 설정
        dto.setContent(guestbookContent.getContent()); // 방명록 내용 설정
        dto.setGuestbookId(guestbookContent.getGuestbook().getGuestbookId()); // 방명록 ID 설정
        dto.setCreatedDate(guestbookContent.getCreatedDate()); // 작성 일자 설정

        // 프로필 이미지 이름은 방명록과 연관된 Member 엔티티에서 가져옴
        dto.setProfileImageName(guestbookContent.getGuestbook().getMember().getProfileImageName());

        return dto; // 변환된 DTO 반환
    }
}
