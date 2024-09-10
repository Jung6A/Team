package com.guestbook.Service;

import com.guestbook.Dto.GuestbookContentDto;
import com.guestbook.Entity.Guestbook;
import com.guestbook.Entity.GuestbookContent;
import com.guestbook.Repository.GuestContentRepository;
import com.guestbook.Repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestContentRepository guestContentRepository;

    public void save(@Valid GuestbookContentDto guestbookContentDto) {
        GuestbookContent guestbookContent=guestbookContentDto.createGuestbook();

        guestContentRepository.save(guestbookContent);
    }

    public Guestbook getGuestbook(Long id) {
        Guestbook guestbook=guestRepository.findByMemberId(id);
        if(guestbook==null){
            throw new UsernameNotFoundException("방명록을 찾을 수 없습니다.");
        }
        return guestbook;
    }
}
