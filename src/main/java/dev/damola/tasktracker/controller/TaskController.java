package dev.damola.tasktracker.controller;

import dev.damola.tasktracker.configuration.UserPrincipal;
import dev.damola.tasktracker.model.AssignRequest;
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
        UserEntity userEntity = getUserFromPrincipal(user);
        return taskRepository.findByOwner(userEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@AuthenticationPrincipal UserPrincipal user
            , @PathVariable Long id){
        UserEntity userEntity = getUserFromPrincipal(user);
        Optional<Task> task = taskRepository.findByOwnerAndId(userEntity,id);
        return task.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@AuthenticationPrincipal UserPrincipal user,
                                           @RequestBody Task task){
        UserEntity userEntity = getUserFromPrincipal(user);
        task.setOwner(userEntity);
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.created(URI.create("api/tasks/"+savedTask.getId())).body(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@AuthenticationPrincipal UserPrincipal user,
                                           @PathVariable Long id,@RequestBody Task task){
        if (!taskRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }

        UserEntity userEntity = getUserFromPrincipal(user);
        if(userEntity == task.getOwner()){
            task.setId(id);
            taskRepository.save(task);
            return ResponseEntity.ok(task);
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@AuthenticationPrincipal UserPrincipal user,
                                           @PathVariable Long id){
        Optional<Task> task =(taskRepository.findById(id));
        UserEntity userEntity = getUserFromPrincipal(user);
        if(userEntity == task.get().getOwner()){
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
      return ResponseEntity.unprocessableEntity().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<Task> assignTask(@AuthenticationPrincipal UserPrincipal user,
                                           @RequestBody AssignRequest assignRequest){
        Long taskId = assignRequest.getTaskId();
        UserEntity assignee = userRepository
                .findByUsername(assignRequest.getAssigneeUsername())
                .get();
        Optional<Task> task = taskRepository.findById(taskId);
        UserEntity userEntity = getUserFromPrincipal(user);
        if(userEntity == task.get().getOwner()){
            task.get().setAssignee(assignee);
            taskRepository.save(task.get());
            return ResponseEntity.ok(task.get());
        }
        return ResponseEntity.badRequest().build();
    }

    private UserEntity getUserFromPrincipal(UserPrincipal user){
        return  UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .id(user.getId())
                .build();
    }
}
