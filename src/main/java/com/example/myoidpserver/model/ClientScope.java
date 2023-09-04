package com.example.myoidpserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "client_scope")
@Entity
@Getter
@Setter
public class ClientScope {

    @EmbeddedId
    private ClientScopeId clientScopeId;

    @ManyToOne
    @MapsId(value = "clientInfoId")
    @JoinColumn(name = "client_info_id")
    private ClientInfo clientInfo;

    @ManyToOne
    @MapsId(value = "scopeId")
    @JoinColumn(name = "scope_id")
    private SystemScope systemScope;
}
