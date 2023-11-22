package com.intuit.profileservice.dto;

import lombok.Data;

@Data
public class UpdateProfileResponseDto {
    private String resCode;
    private String resMessage;
    private String customerId;

}
