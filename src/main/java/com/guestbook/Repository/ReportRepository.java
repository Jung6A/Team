package com.guestbook.Repository;

import com.guestbook.Entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    // 필요시 특정 조회 메소드 추가
}
