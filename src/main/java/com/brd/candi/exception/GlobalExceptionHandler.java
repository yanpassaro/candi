package com.brd.candi.exception;

import com.brd.candi.exception.custom.AlreadyExistsException;
import com.brd.candi.exception.custom.NotAuthorizedException;
import com.brd.candi.exception.custom.NotLoggedException;
import com.brd.candi.http.Response;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

import static java.time.LocalDateTime.now;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ResponseEntity<Response> handleMethodArgumentNotValidException() {
        return ResponseEntity.badRequest().body(Response.builder()
                .mensagem(BAD_REQUEST.getReasonPhrase())
                .data(now())
                .statusCode(BAD_REQUEST.value())
                .status(BAD_REQUEST)
                .build());
    }


    @ExceptionHandler(value = EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public @ResponseBody ResponseEntity<Response> handleEntityNotFound() {
        return ResponseEntity.badRequest().body(Response.builder()
                .mensagem(NOT_FOUND.getReasonPhrase())
                .data(now())
                .statusCode(NOT_FOUND.value())
                .status(NOT_FOUND)
                .build());
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public @ResponseBody ResponseEntity<Response> handleDataIntegrityViolationException() {
        return ResponseEntity.internalServerError().body(Response.builder()
                .mensagem(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .data(now())
                .statusCode(INTERNAL_SERVER_ERROR.value())
                .status(INTERNAL_SERVER_ERROR)
                .build());
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ResponseEntity<Response> handleUserAlreadyExistsException(AlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(Response.builder()
                .mensagem(ex.getMessage())
                .data(now())
                .statusCode(BAD_REQUEST.value())
                .status(BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(value = NotAuthorizedException.class)
    @ResponseStatus(UNAUTHORIZED)
    public @ResponseBody ResponseEntity<Response> handleNotAuthorizedException(NotAuthorizedException ex) {
        return ResponseEntity.badRequest().body(Response.builder()
                .mensagem(ex.getMessage())
                .data(now())
                .statusCode(UNAUTHORIZED.value())
                .status(UNAUTHORIZED)
                .build());
    }

    @ExceptionHandler(value = NotLoggedException.class)
    @ResponseStatus(UNAUTHORIZED)
    public @ResponseBody ResponseEntity<Response> handleNotLoggedException(NotLoggedException ex) {
        return ResponseEntity.badRequest().body(Response.builder()
                .mensagem(ex.getMessage())
                .data(now())
                .statusCode(UNAUTHORIZED.value())
                .status(UNAUTHORIZED)
                .build());
    }
}
