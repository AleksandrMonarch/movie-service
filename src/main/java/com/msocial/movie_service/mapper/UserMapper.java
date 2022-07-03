package com.msocial.movie_service.mapper;

import com.msocial.movie_service.model.db.User;
import com.msocial.movie_service.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}
