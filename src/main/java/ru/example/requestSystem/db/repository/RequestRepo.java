package ru.example.requestSystem.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.requestSystem.db.dao.Request;

public interface RequestRepo extends JpaRepository<Request, Long> {
}
