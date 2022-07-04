package com.msocial.movie_service.security.header_auth;

import com.msocial.movie_service.exception.db.UserNotFoundInDBException;
import com.msocial.movie_service.model.db.User;
import com.msocial.movie_service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HeaderDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public HeaderDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundInDBException(userId));
        return new HeaderDetails(user);
    }
}
