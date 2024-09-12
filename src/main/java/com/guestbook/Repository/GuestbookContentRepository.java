package com.guestbook.Repository;

import com.guestbook.Entity.GuestbookContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookContentRepository extends JpaRepository<GuestbookContent, Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.
}
