package ru.example.requestSystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.requestSystem.db.dao.Request;
import ru.example.requestSystem.db.dto.RequestDto;
import ru.example.requestSystem.db.repository.RequestRepo;
import ru.example.requestSystem.mapper.RequestMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImp implements RequestService {
    private final RequestRepo repo;
    private final RequestMapper mapper;

    @Override
    public List<RequestDto> findAll() {
        return mapper.RequestToDtos(repo.findAll());
    }

    @Override
    public RequestDto findById(Long id) {
        return Optional.of(getById(id)).map(mapper::RequestToDto).get();
    }

    @Override
    public RequestDto save(RequestDto requestDto) {
        return mapper.RequestToDto(repo.save(
                mapper.DtoToRequest(requestDto)));
    }

    @Override
    public void deleteById(Long id) {
        var request = getById(id);
        repo.delete(request);
    }

    private Request getById(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Request not found")
        );
    }
}
