package com.guestbook.Service;

import com.guestbook.Dto.GuestbookContentDto;
import com.guestbook.Entity.Guestbook;
import com.guestbook.Entity.GuestbookContent;
import com.guestbook.Entity.Member;
import com.guestbook.Repository.GuestbookContentRepository;
import com.guestbook.Repository.GuestBookRepository;
import com.guestbook.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestBookRepository guestBookRepository;
    private final GuestbookContentRepository guestbookContentRepository;
    private final MemberRepository memberRepository;

    @Override
    public void saveGuestbookContent(GuestbookContentDto guestbookContentDto) {
        Member member = memberRepository.findByUserId(guestbookContentDto.getWriter())
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        Guestbook guestbook = guestBookRepository.findByGuestbookId(guestbookContentDto.getGuestbookId())
                .orElseThrow(() -> new RuntimeException("방명록을 찾을 수 없습니다."));

        GuestbookContent guestbookContent = new GuestbookContent();
        guestbookContent.setWriter(guestbookContentDto.getWriter());
        guestbookContent.setContent(guestbookContentDto.getContent());
        guestbookContent.setGuestbook(guestbook);

        guestbookContentRepository.save(guestbookContent);
    }

    @Override
    public List<GuestbookContentDto> getAllGuestbookContents() {
        return guestbookContentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GuestbookContentDto getGuestbookContentById(Long guestbookContentId) {
        return guestbookContentRepository.findById(guestbookContentId)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("방명록 콘텐츠를 찾을 수 없습니다."));
    }

    @Override
    public List<GuestbookContentDto> getGuestbookContentsByMemberId(String memberId) {
        return guestBookRepository.findAll().stream()
                .filter(guestbook -> guestbook.getMember().getUserId().equals(memberId))
                .flatMap(guestbook -> guestbook.getContents().stream())
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private GuestbookContentDto convertToDto(GuestbookContent guestbookContent) {
        GuestbookContentDto dto = new GuestbookContentDto();
        dto.setId(guestbookContent.getId());
        dto.setWriter(guestbookContent.getWriter());
        dto.setContent(guestbookContent.getContent());
        dto.setGuestbookId(guestbookContent.getGuestbook().getGuestbookId());
        dto.setCreatedDate(guestbookContent.getCreatedDate());
        return dto;
    }
}
