package com.escalabram.escalabram.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleMaxSizeException(MaxUploadSizeExceededException exc, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                exc.getMessage(),
                request.getDescription(false));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage handleValidationExceptions(MethodArgumentNotValidException exc, WebRequest request) {
//        Map<String, String> errors = new HashMap<>();
        log.error("exception: A controller should have received a DTO that do not fulfill the validation annotations", exc);

//        exc.getBindingResult().getFieldErrors().forEach(error ->
//                errors.put(error.getField(), error.getDefaultMessage()));

        //TODO Errors.toString ou exc.getMessage() ??

        //return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                exc.getMessage(),
                request.getDescription(false));
    }
}
