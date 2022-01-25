package com.techelevator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "PlayDate not found.")
public class PlayDateNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public PlayDateNotFoundException() {
        super("PlayDate not found.");
    }
}
