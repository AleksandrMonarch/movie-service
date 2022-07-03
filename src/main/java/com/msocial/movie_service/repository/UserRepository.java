package com.msocial.movie_service.repository;

import com.msocial.movie_service.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    Boolean existsByEmailOrUsername(String email, String username);
}
