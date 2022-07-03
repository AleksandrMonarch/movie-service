package com.msocial.movie_service.validation.username_validator.validator;

import com.msocial.movie_service.validation.username_validator.annotation.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<Username, String> {

    private static final String USERNAME_REGEX_PATTERN = "^[a-zA-Z]{1,50}$";

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return isValidUsername(username);
    }

    private boolean isValidUsername(String username) {
        if (Objects.nonNull(username)) {
            Pattern pattern = Pattern.compile(USERNAME_REGEX_PATTERN);
            Matcher matcher = pattern.matcher(username);
            return matcher.matches();
        }
        return false;
    }
}
