package com.example.myoidpserver.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "approved_site_scope")
@Entity
@Getter
@Setter
public class ApprovedSiteScope {

    @EmbeddedId
    private ApprovedSiteScopeId id;

    @ManyToOne
    @MapsId(value = "approvedSiteId")
    @JoinColumn(name = "approved_site_id")
    private ApprovedSite approvedSite;

    @ManyToOne
    @MapsId(value = "scopeId")
    @JoinColumn(name = "scope_id")
    private SystemScope systemScope;

}
