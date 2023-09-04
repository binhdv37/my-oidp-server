package com.example.myoidpserver.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "authentication_holder_param")
@Entity
@Getter
@Setter
public class AuthenticationHolderParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "param")
    private String param;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "authentiation_holder_id")
    private AuthenticationHolder authenticationHolder;
}
