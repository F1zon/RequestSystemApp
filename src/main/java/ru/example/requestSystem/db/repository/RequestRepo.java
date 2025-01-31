package ru.example.requestSystem.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.example.requestSystem.db.RequestModel;
import ru.example.requestSystem.db.dao.Request;

import java.util.List;

public interface RequestRepo extends JpaRepository<Request, Long> {
}
