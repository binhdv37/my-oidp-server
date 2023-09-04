package com.example.myoidpserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "system_scope")
@Entity
@Getter
@Setter
public class SystemScope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "scope")
    private String scope;

    @OneToMany(mappedBy = "systemScope")
    private Set<ClientScope> clientScopes;

}
