package com.intuit.profileservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class FetchErrorCodeDBResp {
    List<ErrorCodeDto> errorCodes;
}
