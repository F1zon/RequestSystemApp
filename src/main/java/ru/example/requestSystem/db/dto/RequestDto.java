package ru.example.requestSystem.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private int id;
    private int clientId;
    private int operatorId;
    private int status;
    private String data;
    private String comment;
    private String createdAt;
    private String updatedAt;
}
