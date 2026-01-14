package com.escalabram.escalabram.exception;

import java.util.Date;

public record ErrorMessage(
        int statusCode,
        Date dateTime,
        String message,
        String description
) {}
