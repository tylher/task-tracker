package dev.damola.tasktracker.repository;

import dev.damola.tasktracker.model.Task;
import dev.damola.tasktracker.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByOwner(UserEntity owner);
    Optional<Task> findByOwnerAndId(UserEntity owner,Long id);
}
