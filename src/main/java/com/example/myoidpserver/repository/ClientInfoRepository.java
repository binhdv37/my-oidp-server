package com.example.myoidpserver.repository;

import com.example.myoidpserver.model.ClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientInfoRepository extends JpaRepository<ClientInfo, Long> {

    ClientInfo findByClientId(String clientId);

}
