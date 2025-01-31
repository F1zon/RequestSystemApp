package ru.example.requestSystem.db.enums;

import lombok.Getter;

@Getter
public enum Role {
    CLIENT("client"), OPERATOR("operator");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}
