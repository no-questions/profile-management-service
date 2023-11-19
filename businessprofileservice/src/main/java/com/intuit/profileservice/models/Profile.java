package com.intuit.profileservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.intuit.profileservice.repository.listener.ProfileListener;

@Entity
@EntityListeners(ProfileListener.class)
@Table(name = "businessprofile")
@Data
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String companyname;

    @Column(nullable = false)
    private String legalname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String website;

    @OneToOne(cascade = CascadeType.ALL)
    private TaxIdentifier pandetails;

    @OneToOne(cascade = CascadeType.ALL)
    private TaxIdentifier eindetails;

    @OneToOne(cascade = CascadeType.ALL)
    private Address businessaddress;

    @OneToOne(cascade = CascadeType.ALL)
    private Address legaladdress;
    @Column(nullable = false)
    private Boolean ismodified;
    private Date modifieddate;

    // Getters and setters omitted for brevity
}
