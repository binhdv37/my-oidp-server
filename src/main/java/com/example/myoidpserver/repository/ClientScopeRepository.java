package com.example.myoidpserver.repository;

import com.example.myoidpserver.model.ClientScope;
import com.example.myoidpserver.model.ClientScopeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientScopeRepository extends JpaRepository<ClientScope, ClientScopeId> {
}
