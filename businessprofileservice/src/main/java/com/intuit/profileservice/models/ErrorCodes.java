package com.intuit.profileservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "errorcodes")
@Data
public class ErrorCodes implements Serializable {
    @Id
    @Column(nullable = false)
    String errorcode;
    @Column(nullable = false)
    String errormessage;
    @Column(nullable = false)
    Boolean isfailure;
    @Column(nullable = false)
    Boolean isretryeligible;
}
