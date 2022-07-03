package com.msocial.movie_service.exception.db;

public class UserNotFoundInDBException extends DataBaseException {

    private static final String MESSAGE_PATTERN = "User with id: %s doesn't exist";

    public UserNotFoundInDBException(String userId) {
        super(String.format(MESSAGE_PATTERN, userId));
    }
}
