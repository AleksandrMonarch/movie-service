package com.msocial.movie_service.model.dto;

import com.msocial.movie_service.validation.username_validator.annotation.Username;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {

    private String id;

    @Email
    @NotNull
    private String email;

    @Username
    private String username;

    private String name;
}
