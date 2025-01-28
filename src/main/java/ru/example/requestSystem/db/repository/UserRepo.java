package ru.example.requestSystem.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.requestSystem.db.dao.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
