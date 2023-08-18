package dev.damola.tasktracker.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class Login {
    private String username;
    private String password;
}
