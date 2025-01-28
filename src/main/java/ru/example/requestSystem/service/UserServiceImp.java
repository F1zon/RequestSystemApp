package ru.example.requestSystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.requestSystem.db.dao.User;
import ru.example.requestSystem.db.dto.UserDto;
import ru.example.requestSystem.db.repository.UserRepo;
import ru.example.requestSystem.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImp implements UserService {
    private final UserRepo repo;
    private final UserMapper mapper;

    @Override
    public List<UserDto> findAll() {
        return mapper.UsersToDtos(repo.findAll());
    }

    @Override
    public UserDto findById(Long id) {
        return Optional.of(getById(id)).map(mapper::UserToDto).get();
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        return mapper.UserToDto(repo.save(
                mapper.DtoToUser(userDto)));
    }

//    TODO: Добавить обработчик на null
    @Override
    @Transactional
    public void deleteById(Long id) {
        var user = getById(id);
        repo.delete(user);
    }

    private User getById(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new RuntimeException("User with id " + id + " not found")
        );
    }
}
