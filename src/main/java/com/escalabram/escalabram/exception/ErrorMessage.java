package com.escalabram.escalabram.exception;

import lombok.Getter;

import java.util.Date;

@Getter
public class ErrorMessage { // todo record

    private final int statusCode;
    private final Date dateTime;
    private final String message;
    private final String description;

    public ErrorMessage(int statusCode, Date dateTime, String message, String description) {
        this.statusCode = statusCode;
        this.dateTime = dateTime;
        this.message = message;
        this.description = description;
    }
}
