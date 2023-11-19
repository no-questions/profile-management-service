package com.intuit.profileservice.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.intuit.profileservice.dto.BaseResponse;
import com.intuit.profileservice.service.GetErrorMessages;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {
    @Autowired
    GetErrorMessages getErrorMessages;
    
    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<BaseResponse> handleApplicationException (ApplicationException ex, WebRequest request) {
        return ResponseEntity.ok(new BaseResponse(ex.getErrorCode(),getErrorMessages.fetchErrorMessage(ex.getErrorCode())));
    }
}
