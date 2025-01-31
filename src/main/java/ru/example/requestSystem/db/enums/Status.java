package ru.example.requestSystem.db.enums;

import lombok.Getter;

@Getter
public enum Status {
    NEW("new"), ERROR("error"), FIXED("fixed"), DONE("done");

    private final String status;

    Status(String status) {
        this.status = status;
    }

}
