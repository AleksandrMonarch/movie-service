package com.msocial.movie_service.sevice.impl.db;

import com.msocial.movie_service.exception.db.UserAlreadyExistException;
import com.msocial.movie_service.model.db.User;
import com.msocial.movie_service.model.dto.UserUpdateRequest;
import com.msocial.movie_service.repository.UserRepository;
import com.msocial.movie_service.sevice.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDB implements UserService {

    private final UserRepository userRepository;

    public UserServiceDB(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByEmailOrUsername(user.getEmail(), user.getUsername())) {
            throw new UserAlreadyExistException();
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUserUsernameAndName(User user, UserUpdateRequest updateRequest) {
        user.setUsername(updateRequest.getUsername());
        user.setName(updateRequest.getName());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUserFetchMovies(String userId) {
        return userRepository.getReferenceById(userId);
    }
}
