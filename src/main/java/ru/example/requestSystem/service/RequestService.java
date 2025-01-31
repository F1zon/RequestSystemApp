package ru.example.requestSystem.service;

import ru.example.requestSystem.db.dto.RequestDto;

import java.util.List;

public interface RequestService {
    List<RequestDto> findAll();
    RequestDto findById(Long id);
    void save(RequestDto requestDto);
    void deleteById(Long id);
}
