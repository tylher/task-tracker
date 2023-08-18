package dev.damola.tasktracker.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

//expose setters and getters for user model
@Data
// define unique constraint on username column
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
})
@Entity
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
}
