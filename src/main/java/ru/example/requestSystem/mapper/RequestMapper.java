package ru.example.requestSystem.mapper;

import org.mapstruct.Mapper;
import ru.example.requestSystem.db.dao.Request;
import ru.example.requestSystem.db.dto.RequestDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    Request DtoToRequest(RequestDto dto);

    RequestDto RequestToDto(Request request);

    List<RequestDto> RequestToDtos(List<Request> requests);
}
