package com.example.myoidpserver.repository;

import com.example.myoidpserver.model.ApprovedSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovedSiteRepository extends JpaRepository<ApprovedSite, Long> {
}
