package dev.damola.tasktracker.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    final private String token;
}
