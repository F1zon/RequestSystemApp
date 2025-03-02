package ru.example.requestSystem.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private Long id;
    private Long clientId;
    private Long operatorId;
    private String status;
    private String data;
    private String comment;
    private String createdAt;
    private String updatedAt;
}
