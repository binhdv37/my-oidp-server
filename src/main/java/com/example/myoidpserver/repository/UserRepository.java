package com.example.myoidpserver.repository;

import com.example.myoidpserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = ?1 or u.username = ?2 ")
    Optional<User> findByEmailOrUsername(String email, String username);

}
