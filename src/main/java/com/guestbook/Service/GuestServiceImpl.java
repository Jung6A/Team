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

    //작성된 방명록 저장 메서드
    @Override
    public void saveGuestbookContent(GuestbookContentDto guestbookContentDto) {
        Member member = memberRepository.findByUserId(guestbookContentDto.getWriter()) //작성자 탐색
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        Guestbook guestbook = guestBookRepository.findByGuestbookId(guestbookContentDto.getGuestbookId()) //방명록(누구의 방명록에 작성하는지) 탐색
                .orElseThrow(() -> new RuntimeException("방명록을 찾을 수 없습니다."));

        GuestbookContent guestbookContent = new GuestbookContent();
        guestbookContent.setWriter(guestbookContentDto.getWriter()); //작성자 저장
        guestbookContent.setContent(guestbookContentDto.getContent()); //방명록 내용 저장
        guestbookContent.setGuestbook(guestbook); //방명록 저장

        guestbookContentRepository.save(guestbookContent); //리턴
    }

    @Override
    public List<GuestbookContentDto> getAllGuestbookContents() { //모든 방명록 추출
        return guestbookContentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GuestbookContentDto getGuestbookContentById(Long guestbookContentId) { //특정 방명록 추출(방명록 id)
        return guestbookContentRepository.findById(guestbookContentId)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("방명록 콘텐츠를 찾을 수 없습니다."));
    }

    @Override
    public List<GuestbookContentDto> getGuestbookContentsByMemberId(String memberId) { //특정 방명록 추출 (유저 id)
        List<GuestbookContentDto> guestbookContentDtos = guestBookRepository.findAll().stream()
                .filter(guestbook -> guestbook.getMember().getUserId().equals(memberId))
                .flatMap(guestbook -> guestbook.getContents().stream())
                .map(this::convertToDto)
                .collect(Collectors.toList());

        List<Report> cList = reportRepository.findAll();
        for(Report find : cList){
            for(GuestbookContentDto guestbookContentDto : guestbookContentDtos){
                if (guestbookContentDto.getId() == find.getReportedGuest().getId()) //신고된 게시글일 경우
                    guestbookContentDto.setContent("이 글은 관리자에 의해 삭제된 게시글입니다."); //별개의 메시지 출력
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
