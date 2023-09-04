package com.example.myoidpserver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "approved_site")
@Entity
@Getter
@Setter
public class ApprovedSite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_info_id")
    private ClientInfo clientInfo;

    @Column(name = "approved_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date approvedDate;

    @Column(name = "expired_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date expiredDate;
}
