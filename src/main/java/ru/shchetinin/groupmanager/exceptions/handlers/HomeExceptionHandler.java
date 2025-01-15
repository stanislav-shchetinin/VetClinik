package ru.shchetinin.groupmanager.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.shchetinin.groupmanager.exceptions.*;
import ru.shchetinin.groupmanager.responses.Response;

@ControllerAdvice
public class HomeExceptionHandler {
    @ExceptionHandler({NotRealCreatorException.class})
    public ResponseEntity<Response> handleNotRealCreatorException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.FORBIDDEN.value(), "Permission denied. Is not real admin."),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({GroupAlreadyExistException.class})
    public ResponseEntity<Response> handleGroupAlreadyExistException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.CONFLICT.value(), "Group with this id is already exist."),
                HttpStatus.CONFLICT);
    }
    @ExceptionHandler({NotFoundGroupDeleteException.class})
    public ResponseEntity<Response> handleNotFoundGroupDeleteException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.NO_CONTENT.value(), "Group isn't founded."),
                HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Response> handleUserNotFoundException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.NOT_FOUND.value(), "User isn't founded."),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NotFoundGroupException.class})
    public ResponseEntity<Response> handleNotFoundGroupException(Exception e) {
        return new ResponseEntity<>(
                new Response(HttpStatus.NOT_FOUND.value(), "Group isn't founded."),
                HttpStatus.NOT_FOUND);
    }
}
