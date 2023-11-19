package com.intuit.profileservice.dto;


import lombok.Data;

@Data
public class CreateProfileResponseDto extends BaseResponse{
    private String customerId;
}
