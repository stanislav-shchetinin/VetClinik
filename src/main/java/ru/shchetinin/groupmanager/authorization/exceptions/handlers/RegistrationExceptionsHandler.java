package ru.shchetinin.groupmanager.authorization.exceptions.handlers;

import ru.shchetinin.groupmanager.authorization.exceptions.UserAlreadyExistsException;
import ru.shchetinin.groupmanager.authorization.exceptions.ActivationCodeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.shchetinin.groupmanager.responses.Response;

@ControllerAdvice
public class RegistrationExceptionsHandler {
    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<Response> handleUserAlreadyExistsException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.CONFLICT.value(), "User with this username already exists"),
                HttpStatus.CONFLICT);
    }
    @ExceptionHandler({ActivationCodeNotFoundException.class})
    public ResponseEntity<Response> handleActivationCodeNotFoundException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.NOT_FOUND.value(), "Activation code is not found"),
                HttpStatus.NOT_FOUND);
    }
}