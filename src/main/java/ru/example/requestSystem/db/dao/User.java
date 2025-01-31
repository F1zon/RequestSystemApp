package ru.example.requestSystem.db.dao;

import jakarta.persistence.*;
import lombok.*;
import ru.example.requestSystem.db.enums.Role;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private Role role;

    @Column(name = "password_hash")
    private String passwordHash;
}
