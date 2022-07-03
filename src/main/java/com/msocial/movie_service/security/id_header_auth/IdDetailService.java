package com.msocial.movie_service.security.id_header_auth;

import com.msocial.movie_service.exception.db.UserNotFoundInDBException;
import com.msocial.movie_service.model.db.User;
import com.msocial.movie_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class IdDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public IdDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundInDBException(userId));
        return new org.springframework.security.core.userdetails.User(
                user.getId(), "", Collections.emptyList());
    }
}
