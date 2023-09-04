package com.example.myoidpserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "client_info")
@Entity
@Getter
@Setter
public class ClientInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_secret")
    private String clientSecret;

    @OneToMany(mappedBy = "clientInfo")
    private Set<ClientScope> clientScopes;

    @OneToMany(mappedBy = "clientInfo")
    private Set<ClientRedirectUrl> redirectUrls;

    @OneToMany(mappedBy = "clientInfo")
    private Set<ApprovedSite> approvedSites;

}
