package com.intuit.profileservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends RuntimeException{
    
    private static final long serialVersionUID = 1;

    private String errorCode;
    private String errorMessage;

    public BadRequestException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BadRequestException(String errorCode, String errorMessage, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BadRequestException() { super();}

    public BadRequestException(String message) { super(message); }
}
