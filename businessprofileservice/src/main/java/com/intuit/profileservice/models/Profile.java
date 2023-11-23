package com.intuit.profileservice.models;

import com.intuit.profileservice.repository.listener.ProfileListener;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@EntityListeners(ProfileListener.class)
@Table(name = "businessprofile", indexes = {
        @Index(columnList = "legalname",name = "businessprofile_legalname_idx")
})
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id) &&
                Objects.equals(companyname, profile.companyname) &&
                Objects.equals(legalname, profile.legalname) &&
                Objects.equals(email, profile.email) &&
                Objects.equals(website, profile.website) &&
                Objects.equals(pandetails, profile.pandetails) &&
                Objects.equals(eindetails, profile.eindetails) &&
                Objects.equals(businessaddress, profile.businessaddress) &&
                Objects.equals(legaladdress, profile.legaladdress);
    }

}
