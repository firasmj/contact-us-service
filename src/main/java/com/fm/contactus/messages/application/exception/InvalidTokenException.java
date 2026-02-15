package com.fm.contactus.messages.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Invalid token provided")
public class InvalidTokenException extends RuntimeException {    
    public InvalidTokenException(String message) {
        super(message);
    }
}
