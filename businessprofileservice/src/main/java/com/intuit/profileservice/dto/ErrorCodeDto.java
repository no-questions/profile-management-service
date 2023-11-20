package com.intuit.profileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorCodeDto {
    String errorcode;
    String errormessage;
    Boolean isfailure;
    Boolean isretryeligible;
}
