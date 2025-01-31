package ru.example.requestSystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.requestSystem.db.dao.User;
import ru.example.requestSystem.db.dto.UserDto;
import ru.example.requestSystem.db.enums.Role;
import ru.example.requestSystem.db.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {
    private final UserRepo repo;

    @Override
    public List<UserDto> findAll() {
        List<UserDto> dtos = new ArrayList<>();
        List<User> users = repo.findAll();

        for (User user : users) {
            dtos.add(userToDto(user));
        }

        return dtos;
    }

    @Override
    public UserDto findById(Long id) {
        User user = repo.findById(id).get();
        return userToDto(user);
    }

    @Override
    public void save(UserDto userDto) {
        User user = repo.save(dtoToUser(userDto));
    }

    @Override
    public void deleteById(Long id) {
        var user = getById(id);
        repo.delete(user);
    }

    private User getById(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new RuntimeException("User with id " + id + " not found")
        );
    }

    private UserDto userToDto(User user) {
        return new UserDto(user.getId(), user.getName(),
                user.getRole().name(), user.getPasswordHash());
    }

    private User dtoToUser(UserDto dto) {
        return new User(dto.getId(), dto.getName(),
                Role.valueOf(dto.getRole()), dto.getPasswordHash());
    }
}
