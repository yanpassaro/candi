package com.brd.candi.exception;

import com.brd.candi.domain.model.Response;
import com.brd.candi.exception.custom.*;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

import static java.time.LocalDate.now;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ResponseEntity<Response> handleMethodArgumentNotValidException() {
        return ResponseEntity.badRequest().body(Response.builder()
                .message(BAD_REQUEST.getReasonPhrase())
                .date(now())
                .statusCode(BAD_REQUEST.value())
                .status(BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(value = JSONException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ResponseEntity<Response> handleNotLoggedException() {
        return ResponseEntity.badRequest().body(Response.builder()
                .message("Json invalido")
                .date(now())
                .statusCode(BAD_REQUEST.value())
                .status(BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ResponseEntity<Response> handleEntityNotFound() {
        return ResponseEntity.badRequest().body(Response.builder()
                .message(BAD_REQUEST.getReasonPhrase())
                .date(now())
                .statusCode(BAD_REQUEST.value())
                .status(BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public @ResponseBody ResponseEntity<Response> handleDataIntegrityViolationException() {
        return ResponseEntity.internalServerError().body(Response.builder()
                .message(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .date(now())
                .statusCode(INTERNAL_SERVER_ERROR.value())
                .status(INTERNAL_SERVER_ERROR)
                .build());
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ResponseEntity<Response> handleUserAlreadyExistsException(AlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(Response.builder()
                .message(ex.getMessage())
                .date(now())
                .statusCode(BAD_REQUEST.value())
                .status(BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(value = NotAuthorizedException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ResponseEntity<Response> handleNotAuthorizedException(NotAuthorizedException ex) {
        return ResponseEntity.badRequest().body(Response.builder()
                .message(ex.getMessage())
                .date(now())
                .statusCode(BAD_REQUEST.value())
                .status(BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(value = NotLoggedException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ResponseEntity<Response> handleNotLoggedException(NotLoggedException ex) {
        return ResponseEntity.badRequest().body(Response.builder()
                .message(ex.getMessage())
                .date(now())
                .statusCode(BAD_REQUEST.value())
                .status(BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(value = NotExistException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ResponseEntity<Response> handleNotExistException(NotExistException ex) {
        return ResponseEntity.badRequest().body(Response.builder()
                .message(ex.getMessage())
                .date(now())
                .statusCode(BAD_REQUEST.value())
                .status(BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(value = IncorrectCedentialsException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ResponseEntity<Response> handleIncorrectCedentialsException(IncorrectCedentialsException ex) {
        return ResponseEntity.badRequest().body(Response.builder()
                .message(ex.getMessage())
                .date(now())
                .statusCode(BAD_REQUEST.value())
                .status(BAD_REQUEST)
                .build());
    }
}
