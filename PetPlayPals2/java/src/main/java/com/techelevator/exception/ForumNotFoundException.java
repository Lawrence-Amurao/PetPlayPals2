package com.techelevator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Forum not found.")
public class ForumNotFoundException extends Exception {
        private static final long serialVersionUID = 1L;

        public ForumNotFoundException() {
            super("Forum not found.");
        }
}


