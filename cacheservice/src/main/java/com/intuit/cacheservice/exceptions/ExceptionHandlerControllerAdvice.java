package com.intuit.cacheservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.intuit.cacheservice.dto.BaseResponse;
import com.intuit.cacheservice.repository.ErrorCodesCacheRepository;

import lombok.RequiredArgsConstructor;


@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {
    private final ErrorCodesCacheRepository errorCodesCacheRepository;

    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<BaseResponse> handleApplicationException (ApplicationException ex, WebRequest request) {
        return ResponseEntity.ok(new BaseResponse(ex.getErrorCode(),errorCodesCacheRepository.findByErrorcode(ex.getErrorCode()).getErrormessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<BaseResponse> handleBadRequestException (BadRequestException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(ex.getErrorCode(),errorCodesCacheRepository.findByErrorcode(ex.getErrorCode()).getErrormessage()));
    }
}
