package com.guestbook.Service;

import com.guestbook.Dto.GuestbookContentDto;
import com.guestbook.Entity.GuestbookContent;
import com.guestbook.Repository.GuestContentRepository;
import com.guestbook.Repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private GuestContentRepository guestContentRepository;

    public void save(@Valid GuestbookContentDto guestbookContentDto) {
        GuestbookContent guestbookContent=guestbookContentDto.createGuestbook();

        guestContentRepository.save(guestbookContent);
    }
}
