package com.example.myoidpserver.repository;

import com.example.myoidpserver.model.AuthenticationHolderScope;
import com.example.myoidpserver.model.AuthenticationHolderScopeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationHolderScopeRepository extends JpaRepository<AuthenticationHolderScope, AuthenticationHolderScopeId> {
}
