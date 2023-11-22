package com.intuit.profileservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1;

    private String errorCode;
    private String errorMessage;

    public NotFoundException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public NotFoundException(String errorCode, String errorMessage, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public NotFoundException() { super();}

    public NotFoundException(String message) { super(message); }
}
