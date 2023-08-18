package dev.damola.tasktracker.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String title;
    private String description;
    private LocalDate  dueDate;
    private Boolean completed;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private UserEntity assignee;

//    define constructor for Task entity class
    public Task(){

    }
    public Task(Long id, String title, String description, LocalDate dueDate, Boolean completed){
        this.id=id;
        this.title= title;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    // define data accessors(getters and setters)
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
