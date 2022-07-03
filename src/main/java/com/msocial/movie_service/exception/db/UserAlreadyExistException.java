package com.msocial.movie_service.exception.db;

public class UserAlreadyExistException extends DataBaseException {

    private static final String MESSAGE = "User with such email/username already exist";

    public UserAlreadyExistException() {
        super(MESSAGE);
    }
}
