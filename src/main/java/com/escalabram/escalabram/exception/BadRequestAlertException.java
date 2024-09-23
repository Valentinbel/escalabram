package com.escalabram.escalabram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestAlertException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1687764527730255043L;

    public  BadRequestAlertException(String s){
        super(s);
    }
}