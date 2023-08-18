package dev.damola.tasktracker.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AssignRequest {
    private String AssigneeUsername;
    private Long taskId;
}
