package org.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.example.entity.Task;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * DTO d'entré.
 * Permet de controler ce que l'API reçoit.
 */
public class TaskInput {

    private String name;
    private String description;

    // Valeurs possibles LOW, MEDIUM, HIGH
    private String priority;

    // Valeurs possibles TODO, IN_PROGRESS, DONE
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime creationDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deadline;

    public TaskInput() {
    }

    public TaskInput(String name, String description, String priority, String status, LocalDateTime creationDate, LocalDateTime deadline) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.creationDate = creationDate;
        this.deadline = deadline;
    }

    public static TaskInput fromEntity(Task task) {
        return new TaskInput(
            task.getName(),
            task.getDescription(),
            task.getPriority(),
            task.getStatus(),
            task.getCreationDate(),
            task.getDeadline()
        );
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
