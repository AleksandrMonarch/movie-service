package com.msocial.movie_service.sevice.impl.db;

import com.msocial.movie_service.exception.auth.MovieServiceAuthenticationException;
import com.msocial.movie_service.model.db.User;
import com.msocial.movie_service.security.header_auth.HeaderDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticatedUserService {

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new MovieServiceAuthenticationException();
        }
        return ((HeaderDetails) authentication.getPrincipal()).getUser();
    }
}
