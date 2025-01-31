package ru.example.requestSystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.requestSystem.db.RequestModel;
import ru.example.requestSystem.db.dao.Request;
import ru.example.requestSystem.db.dto.RequestDto;
import ru.example.requestSystem.db.enums.Status;
import ru.example.requestSystem.db.repository.RequestRepo;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImp implements RequestService {

    private final RequestRepo repo;

    @Override
    public List<RequestDto> findAll() {
        List<RequestDto> dtos = new ArrayList<>();
        List<Request> requests = repo.findAll();

        for (Request request : requests) {
            dtos.add(requestToDto(request));
        }

        return dtos;
    }

    @Override
    public RequestDto findById(Long id) {
        Request request = repo.findById(id).get();
        return requestToDto(request);
    }

    @Override
    public void save(RequestDto requestDto) {
        repo.save(dtoToRequest(requestDto));
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

    private RequestDto requestToDto(Request request) {
        return new RequestDto(request.getId(), request.getClientId(),
                request.getOperatorId(), request.getStatus().getStatus(),
                request.getData(), request.getComment(),
                request.getCreatedAt(), request.getUpdatedAt());
    }

    private Request dtoToRequest(RequestDto dto) {
        return new Request(dto.getId(), dto.getClientId(),
                dto.getOperatorId(), Status.valueOf(dto.getStatus()),
                dto.getData(), dto.getComment(),
                dto.getCreatedAt(), dto.getUpdatedAt());
    }
}
