package com.example.myoidpserver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Table(name = "authentication_holder")
@Entity
@Getter
@Setter
public class AuthenticationHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "client_info_id")
    private Long clientInfoId;

    @Column(name = "redirect_url")
    private String redirectUrl;

    @Column(name = "request_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date requestDate;

    @Column(name = "expired_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date expiredDate;

    @OneToOne(mappedBy = "authenticationHolder")
    private AuthorizationCode authorizationCode;

    @OneToMany(mappedBy = "authenticationHolder")
    private Set<AuthenticationHolderParam> params;

}
