package com.app.exceptions;

public class UserDeletedException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UserDeletedException() {
        super();
    }

    public UserDeletedException(String message) {
        super(message);
    }

    public UserDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
