package com.guestbook.Service;

import com.guestbook.Dto.GuestbookContentDto;
import com.guestbook.Entity.Guestbook;
import com.guestbook.Entity.GuestbookContent;
import com.guestbook.Entity.Member;
import com.guestbook.Entity.Report;
import com.guestbook.Repository.GuestbookContentRepository;
import com.guestbook.Repository.GuestBookRepository;
import com.guestbook.Repository.MemberRepository;
import com.guestbook.Repository.ReportRepository;
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
    private final ReportRepository reportRepository;

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
        List<GuestbookContentDto> guestbookContentDtos = guestBookRepository.findAll().stream()
                .filter(guestbook -> guestbook.getMember().getUserId().equals(memberId))
                .flatMap(guestbook -> guestbook.getContents().stream())
                .map(this::convertToDto)
                .collect(Collectors.toList());

        List<Report> cList = reportRepository.findAll();
        for (Report find : cList) {
            for (GuestbookContentDto guestbookContentDto : guestbookContentDtos) {
                if (guestbookContentDto.getId().equals(find.getReportedGuest().getId())) {
                    guestbookContentDto.setContent("이 글은 관리자에 의해 삭제된 게시글 입니다.");
                }
            }
        }

        // 댓글 작성자의 프로필 이미지 이름 추가
        for (GuestbookContentDto dto : guestbookContentDtos) {
            Member writerMember = memberRepository.findByUserId(dto.getWriter())
                    .orElse(null);
            if (writerMember != null) {
                dto.setProfileImageName(writerMember.getProfileImageName());
            } else {
                dto.setProfileImageName(null); // 없을 경우 null 설정
            }
        }

        return guestbookContentDtos;
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
