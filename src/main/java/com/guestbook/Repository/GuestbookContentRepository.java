package com.guestbook.Repository;

import com.guestbook.Entity.GuestbookContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface GuestbookContentRepository extends JpaRepository<GuestbookContent, Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.

//    @Query("select gc from GuestbookContent gc where gc.guestbook.member.userId= :guestbookId order by gc.createdDate desc")
//    List<GuestbookContent> findByGuestbookContents(@Param("userId") String userId, Pageable pageable);
//
//    @Query("select COUNT(gc) from GuestbookContent gc where gc.guestbook.member.userId= :userId")
//    Long countGuestbook(@Param("userId") String userId);
}
