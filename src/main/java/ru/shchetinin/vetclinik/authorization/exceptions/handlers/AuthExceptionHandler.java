package ru.shchetinin.vetclinik.authorization.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.shchetinin.vetclinik.authorization.exceptions.UserIsNotActiveException;
import ru.shchetinin.vetclinik.responses.Response;

@ControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<Response> handleUsernameNotFoundException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({UserIsNotActiveException.class})
    public ResponseEntity<Response> handleUserIsNotActiveException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.UNAUTHORIZED.value(), e.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }
}
