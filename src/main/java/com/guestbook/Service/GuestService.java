package com.guestbook.Service;

import com.guestbook.Dto.GuestbookContentDto;
import java.util.List;

public interface GuestService {
    void saveGuestbookContent(GuestbookContentDto guestbookContentDto);
    List<GuestbookContentDto> getAllGuestbookContents();
    GuestbookContentDto getGuestbookContentById(Long guestbookId);
    List<GuestbookContentDto> getGuestbookContentsByMemberId(String memberId);
}
