package com.example.myoidpserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class AuthenticationHolderScopeId implements Serializable {

    @Column(name = "authentication_holder_id")
    private Long authenticationHolderId;

    @Column(name = "scope_id")
    private Long scopeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationHolderScopeId that = (AuthenticationHolderScopeId) o;
        return Objects.equals(authenticationHolderId, that.authenticationHolderId) && Objects.equals(scopeId, that.scopeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authenticationHolderId, scopeId);
    }
}
