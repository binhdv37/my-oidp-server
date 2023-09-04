package com.example.myoidpserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "client_redirect_url")
@Entity
@Getter
@Setter
public class ClientRedirectUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "client_info_id")
    private ClientInfo clientInfo;
}
