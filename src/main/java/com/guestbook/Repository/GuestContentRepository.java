package com.guestbook.Repository;

import com.guestbook.Entity.GuestbookContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestContentRepository extends JpaRepository<GuestbookContent, Long> {
}
