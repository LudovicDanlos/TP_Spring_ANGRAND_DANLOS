package org.example.dto;

import org.example.entity.Task;

import java.util.Date;

/**
 * DTO de sortie.
 * Permet de controler ce que l'API renvoie.
 */
public class TaskResponse {

    private Long id;
    private String titre;
    private String description;
    private boolean priorite;
    private String status;
    private Date creationDate;
    private Date deadline;

    public TaskResponse() {
    }

    public TaskResponse(Long id, String titre, String description, boolean priorite, String status, Date creationDate, Date deadline) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.priorite = priorite;
        this.status = status;
        this.creationDate = creationDate;
        this.deadline = deadline;

    }

    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitre(),
                task.getDescription(),
                task.getPriorite(),
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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPriorite() {
        return priorite;
    }

    public void setPriorite(boolean priorite) {
        this.priorite = priorite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
