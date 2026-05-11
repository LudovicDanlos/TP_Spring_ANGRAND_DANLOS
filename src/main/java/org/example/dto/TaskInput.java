package org.example.dto;

import org.example.entity.Task;

/**
 * DTO de sortie.
 * Permet de controler ce que l'API renvoie.
 */
public class TaskInput {

    private Long id;
    private String name;
    private String description;
    private boolean done;

    public TaskInput() {
    }

    public TaskInput(Long id, String name, String description, boolean done) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.done = done;
    }

    public static TaskInput fromEntity(Task task) {
        return new TaskInput(
            task.getId(),
            task.getName(),
            task.getDescription(),
            task.isDone()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }
}
