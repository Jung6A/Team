package com.guestbook.Dto;

import com.guestbook.Entity.GuestbookContent;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class GuestbookContentDto {
    private Long id;

    private String writer;
    private String content;

    private static ModelMapper modelMapper=new ModelMapper();

    public GuestbookContent createGuestbook() {
        GuestbookContent guestbookContent=new GuestbookContent();
        guestbookContent.setContent(this.content);
        guestbookContent.setWriter(this.writer);

        return guestbookContent;
    }
}
