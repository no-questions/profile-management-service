package com.intuit.cacheservice.dto;


import lombok.Data;

@Data
public class ErrorCodeDto {
    String errorcode;
    String errormessage;
    Boolean isfailure;
    Boolean isretryeligible;
}
