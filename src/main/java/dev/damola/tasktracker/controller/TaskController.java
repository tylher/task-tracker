package dev.damola.tasktracker.controller;

import dev.damola.tasktracker.configuration.UserPrincipal;
import dev.damola.tasktracker.model.Task;
import dev.damola.tasktracker.model.UserEntity;
import dev.damola.tasktracker.repository.TaskRepository;
import dev.damola.tasktracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;
    @GetMapping()
    public List<Task> getTasks(@AuthenticationPrincipal UserPrincipal user){
        UserEntity userEntity = UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .id(user.getId())
                .build();

        System.out.println(userEntity.getUsername());
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        Optional<Task> task = taskRepository.findById(id);
        return task.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.created(URI.create("api/tasks/"+savedTask.getId())).body(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,@RequestBody Task task){
        if (!taskRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        task.setId(id);
        taskRepository.save(task);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id){
       taskRepository.deleteById(id);
       return ResponseEntity.noContent().build();
    }

}
