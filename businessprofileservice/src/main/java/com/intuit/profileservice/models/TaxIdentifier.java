package com.intuit.profileservice.models;

import com.intuit.profileservice.repository.listener.TaxIdentifierListener;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "taxidentifiers")
@EntityListeners(TaxIdentifierListener.class)
@Data
public class TaxIdentifier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taxiden_id_seq")
    @SequenceGenerator(name = "taxiden_id_seq", sequenceName = "taxiden_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String identifier;

    // @ManyToOne
    // @JoinColumn(nullable = false)
    // private Profile businessprofile;

    public enum TaxIDType {
        PAN,
        EIN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxIdentifier that = (TaxIdentifier) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(identifier, that.identifier);
    }
    // Getters and setters omitted for brevity
}