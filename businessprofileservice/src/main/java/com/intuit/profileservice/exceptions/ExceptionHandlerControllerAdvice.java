package com.intuit.profileservice.exceptions;

import com.intuit.profileservice.dto.BaseResponse;
import com.intuit.profileservice.service.GetErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {
    @Autowired
    GetErrorMessages getErrorMessages;

    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<BaseResponse> handleApplicationException(ApplicationException ex, WebRequest request) {
        BaseResponse baseResponse = new BaseResponse(ex.getErrorCode(), getErrorMessages.fetchErrorMessage(ex.getErrorCode()));
        logger.error("error occured returning response: " + baseResponse);
        return ResponseEntity.ok(baseResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<BaseResponse> handleBadRequestException(BadRequestException ex, WebRequest request) {
        BaseResponse baseResponse = new BaseResponse(ex.getErrorCode(), getErrorMessages.fetchErrorMessage(ex.getErrorCode()));
        logger.error("error occured returning response: " + baseResponse);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<BaseResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        BaseResponse baseResponse = new BaseResponse(ex.getErrorCode(), getErrorMessages.fetchErrorMessage(ex.getErrorCode()));
        logger.error("error occured returning response: " + baseResponse);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
    }
}
