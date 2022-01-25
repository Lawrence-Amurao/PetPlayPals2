package com.techelevator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Pet not found.")
public class PetNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public PetNotFoundException() {
        super("Pet not found.");
    }
}
