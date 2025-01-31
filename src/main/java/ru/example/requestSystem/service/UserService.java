package ru.example.requestSystem.service;

import ru.example.requestSystem.db.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    UserDto findById(Long id);
    void save(UserDto userDto);
    void deleteById(Long id);
}
