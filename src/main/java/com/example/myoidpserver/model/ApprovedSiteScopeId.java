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
public class ApprovedSiteScopeId implements Serializable {

    @Column(name = "approved_site_id")
    private Long approvedSiteId;

    @Column(name = "scope_id")
    private Long scopeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApprovedSiteScopeId that = (ApprovedSiteScopeId) o;
        return Objects.equals(approvedSiteId, that.approvedSiteId) && Objects.equals(scopeId, that.scopeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(approvedSiteId, scopeId);
    }
}
