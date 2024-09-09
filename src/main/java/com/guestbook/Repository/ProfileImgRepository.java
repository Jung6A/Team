package com.guestbook.Repository;

import com.guestbook.Entity.ProfileImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImgRepository extends JpaRepository<ProfileImg, Long> {
    ProfileImg findByIdAndRepImgYn(Long id, String repImgYn);
}
