package com.example.myoidpserver.repository;

import com.example.myoidpserver.model.ApprovedSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovedSiteRepository extends JpaRepository<ApprovedSite, Long> {

    @Query(value = "select * from approved_site where user_id = ?1 and cient_info_id = ?2", nativeQuery = true)
    ApprovedSite findByUserIdAndClientInfoId(Long userId, Long clientInfoId);

}
