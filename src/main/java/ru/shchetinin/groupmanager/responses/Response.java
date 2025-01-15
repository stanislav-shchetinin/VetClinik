package ru.shchetinin.groupmanager.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@RequiredArgsConstructor
public class Response {
    private final int codeStatus;
    private final String message;
}