package ru.example.requestSystem.db.dao;

import jakarta.persistence.*;
import lombok.*;
import ru.example.requestSystem.db.enums.Status;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "request")
public class Request {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "operator_id")
    private Long operatorId;

    @Column(name = "status")
//    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "data")
    private String data;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "update_at")
    private String updatedAt;
}
