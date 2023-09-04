package com.example.myoidpserver.repository;

import com.example.myoidpserver.model.ApprovedSiteScope;
import com.example.myoidpserver.model.ApprovedSiteScopeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovedSiteScopeRepository extends JpaRepository<ApprovedSiteScope, ApprovedSiteScopeId> {
}
