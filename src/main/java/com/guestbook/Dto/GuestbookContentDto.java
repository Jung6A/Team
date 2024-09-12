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

    // GuestbookContent를 GuestbookContentDto로 변환하는 static 메서드
    public static GuestbookContentDto of(GuestbookContent guestbookContent) {
        GuestbookContentDto dto = new GuestbookContentDto();
        dto.setId(guestbookContent.getId());
        dto.setWriter(guestbookContent.getWriter());
        dto.setContent(guestbookContent.getContent());
        dto.setGuestbookId(guestbookContent.getGuestbook().getGuestbookId()); // 관계된 Guestbook의 ID를 가져온다
        dto.setCreatedDate(guestbookContent.getCreatedDate());
        return dto;
    }
}
