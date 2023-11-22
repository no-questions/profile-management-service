package com.intuit.profileservice.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auditlog")
@NoArgsConstructor
@Data
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_id_seq")
    @SequenceGenerator(name = "audit_id_seq", sequenceName = "audit_id_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String identifier;
    @Column(nullable = false)
    private String action;
    @Column(nullable = false, length = 30000)
    private String currentprofile;
}
