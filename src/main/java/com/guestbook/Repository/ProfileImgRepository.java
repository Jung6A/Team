package com.guestbook.Repository;

import com.guestbook.Dto.ProfileImgDto;
import com.guestbook.Entity.ProfileImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileImgRepository extends JpaRepository<ProfileImg, Long> {

    static List<ProfileImg> findItem(){

        return List.of();
    }

    ProfileImg findByItemIdAndRepImgYn(long id, String y);
}
