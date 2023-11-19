package com.intuit.validationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidateProfileResponse {
    List<BaseResponse> res;
}
