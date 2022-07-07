package com.msocial.movie_service.controller;

import com.msocial.movie_service.mapper.UserMapper;
import com.msocial.movie_service.model.db.User;
import com.msocial.movie_service.model.dto.BaseResponse;
import com.msocial.movie_service.model.dto.DataResponse;
import com.msocial.movie_service.model.dto.UserDto;
import com.msocial.movie_service.model.dto.UserUpdateRequest;
import com.msocial.movie_service.sevice.UserService;
import com.msocial.movie_service.sevice.impl.db.AuthenticatedUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.msocial.movie_service.constant.Endpoints.*;

@RestController
public class UserController {

    private final AuthenticatedUserService authenticatedUserService;

    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(AuthenticatedUserService authenticatedUserService,
                          UserService userService,
                          UserMapper userMapper) {
        this.authenticatedUserService = authenticatedUserService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(API_USER)
    public DataResponse<UserDto> getUser() {
        User user = authenticatedUserService.getAuthenticatedUser();
        return new DataResponse<>(true, HttpStatus.OK, userMapper.userToUserDto(user));
    }

    @PostMapping(API_USER_REGISTRATION)
    public DataResponse<UserDto> registrationUser(@RequestBody @Valid UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        user = userService.createUser(user);
        return new DataResponse<>(true, HttpStatus.OK, userMapper.userToUserDto(user));
    }

    @PutMapping(API_USER_UPDATE)
    public DataResponse<UserDto> updateUserUsernameAndName(@RequestBody @Valid UserUpdateRequest updateRequest) {
        User user = authenticatedUserService.getAuthenticatedUser();
        return new DataResponse<>(
                true, HttpStatus.OK,
                userMapper.userToUserDto(userService.updateUserUsernameAndName(user, updateRequest)));
    }

    @DeleteMapping(API_USER_DELETE)
    public BaseResponse deleteUser() {
        User user = authenticatedUserService.getAuthenticatedUser();
        userService.deleteUser(user);
        return new BaseResponse(true, HttpStatus.OK);
    }
}