package com.example.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BussinessRuleException
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BussinessRuleException extends RuntimeException {


    /**
     *
     */
    private static final long serialVersionUID = 8894997794956849935L;

    public BussinessRuleException(String message) {
        super(message);
    }

    public BussinessRuleException(String message, Throwable e) {
        super(message, e);
    }
}