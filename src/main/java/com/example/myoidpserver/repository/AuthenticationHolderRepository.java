package com.example.myoidpserver.repository;

import com.example.myoidpserver.model.AuthenticationHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationHolderRepository extends JpaRepository<AuthenticationHolder, Long> {
}
