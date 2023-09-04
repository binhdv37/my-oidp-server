package com.example.myoidpserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ClientScopeId implements Serializable {
    @Column(name = "client_info_id")
    private Long clientInfoId;

    @Column(name = "scope_id")
    private Long scopeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientScopeId that = (ClientScopeId) o;
        return Objects.equals(clientInfoId, that.clientInfoId) && Objects.equals(scopeId, that.scopeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientInfoId, scopeId);
    }
}
