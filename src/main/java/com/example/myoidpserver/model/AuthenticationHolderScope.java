package com.example.myoidpserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "authentication_holder_scope")
@Entity
@Getter
@Setter
public class AuthenticationHolderScope {

    @EmbeddedId
    private AuthenticationHolderScopeId id;

    @ManyToOne
    @MapsId(value = "authenticationHolderId")
    @JoinColumn(name = "authentication_holder_id")
    private AuthenticationHolder authenticationHolder;

    @ManyToOne
    @MapsId(value = "scopeId")
    @JoinColumn(name = "scope_id")
    private SystemScope systemScope;

}
