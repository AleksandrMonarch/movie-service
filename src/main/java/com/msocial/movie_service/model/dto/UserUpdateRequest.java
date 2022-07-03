package com.msocial.movie_service.model.dto;

import com.msocial.movie_service.validation.username_validator.annotation.Username;
import lombok.Data;

@Data
public class UserUpdateRequest {

    @Username
    private String username;

    private String name;
}
