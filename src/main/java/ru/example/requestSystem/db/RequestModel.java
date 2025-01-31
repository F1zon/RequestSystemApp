package ru.example.requestSystem.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestModel {
    private Long id;
    private String clientId;
    private String operatorId;
    private String status;
    private String data;
    private String comment;
    private String createdAt;
    private String updatedAt;
}
