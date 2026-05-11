package org.example.dto;

import org.example.entity.Task;

import java.util.Date;

/**
 * DTO de sortie.
 * Permet de controler ce que l'API renvoie.
 */
public class TaskInput {

    private String titre;
    private String description;

    // Valeurs possibles LOW, MEDIUM, HIGH
    private String priorite;

    // Valeurs possibles TODO, IN_PROGRESS, DONE
    private String status;

    private Date creationDate;
    private Date deadline;

    public TaskInput() {
    }

    public TaskInput(String titre, String description, String priorite, String status, Date creationDate, Date deadline) {
        this.titre = titre;
        this.description = description;
        this.priorite = priorite;
        this.status = status;
        this.creationDate = creationDate;
        this.deadline = deadline;
    }

    public static TaskInput fromEntity(Task task) {
        return new TaskInput(
            task.getTitre(),
            task.getDescription(),
            task.getPriorite(),
            task.getStatus(),
            task.getCreationDate(),
            task.getDeadline()
        );
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getPriorite() {
        return priorite;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getDeadline() {
        return deadline;
    }
}
