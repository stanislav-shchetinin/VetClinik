package ru.shchetinin.groupmanager.authorization.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserIsNotActiveException extends RuntimeException{
    private String message;
    public UserIsNotActiveException(String message) {
        super(message);
    }
}
