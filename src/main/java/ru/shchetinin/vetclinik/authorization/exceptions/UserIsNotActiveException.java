package ru.shchetinin.vetclinik.authorization.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserIsNotActiveException extends RuntimeException{
    private String message;
    public UserIsNotActiveException(String message) {
        super(message);
    }
}
