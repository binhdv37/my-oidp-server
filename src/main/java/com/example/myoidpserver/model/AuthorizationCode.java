package com.example.myoidpserver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "authorization_code")
@Entity
@Getter
@Setter
public class AuthorizationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "expired_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date expiredDate;

    @OneToOne
    @JoinColumn(name = "authentication_holder_id", referencedColumnName = "id")
    private AuthenticationHolder authenticationHolder;

}
