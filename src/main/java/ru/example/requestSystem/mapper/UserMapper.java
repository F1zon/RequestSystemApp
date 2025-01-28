package ru.example.requestSystem.mapper;

import org.mapstruct.Mapper;
import ru.example.requestSystem.db.dao.User;
import ru.example.requestSystem.db.dto.UserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User DtoToUser(UserDto dto);

    UserDto UserToDto(User user);

    List<UserDto> UsersToDtos(List<User> users);
}
