package ru.example.requestSystem.db.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        return role == null ? null : role.getRole();
    }

    @Override
    public Role convertToEntityAttribute(String role) {
        return Stream.of(Role.values())
                .filter(r -> r.getRole().equals(role))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
