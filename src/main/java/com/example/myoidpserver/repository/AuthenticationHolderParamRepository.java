package com.example.myoidpserver.repository;

import com.example.myoidpserver.model.AuthenticationHolderParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationHolderParamRepository extends JpaRepository<AuthenticationHolderParam, Long> {
}
