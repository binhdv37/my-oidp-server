package com.example.myoidpserver.repository;

import com.example.myoidpserver.model.ClientRedirectUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRedirectUrlRepository extends JpaRepository<ClientRedirectUrl, Long> {
}
