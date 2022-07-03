package com.msocial.movie_service.sevice;

import com.msocial.movie_service.model.db.User;
import com.msocial.movie_service.model.dto.UserUpdateRequest;
import org.springframework.data.jpa.repository.Query;

public interface UserService {

    User createUser(User user);

    User updateUserUsernameAndName(User user, UserUpdateRequest updateRequest);

    void deleteUser(User user);

    @Query(value = "select u from User u join fetch Movie m where u.id = :userId")
    User getUserFetchMovies(String userId);
}
