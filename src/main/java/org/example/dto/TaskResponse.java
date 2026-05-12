package org.example.dto;

import org.example.entity.Task;

import java.time.LocalDateTime;

/**
 * DTO de sortie.
 * Permet de controler ce que l'API renvoie.
 */
public class TaskResponse {

    private Long id;
    private String name;
    private String description;
    private String priority;
    private String status;
    private LocalDateTime creationDate;
    private LocalDateTime deadline;

    public TaskResponse() {
    }

    public TaskResponse(Long id, String name, String description, String priority, String status, LocalDateTime creationDate, LocalDateTime deadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.creationDate = creationDate;
        this.deadline = deadline;

    }

    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getCreationDate(),
                task.getDeadline()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
