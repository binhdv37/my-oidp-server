package com.example.myoidpserver.repository;

import com.example.myoidpserver.model.SystemScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemScopeRepository extends JpaRepository<SystemScope, Long> {

    List<SystemScope> findAllByScopeIn(List<String> scope);

}
