package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entite volontairement generique.
 * Les eleves peuvent la garder telle quelle ou la renommer selon leur sujet.
 *
 * Exemples :
 * - Item -> Book
 * - Item -> Movie
 * - Item -> Expense
 * - Item -> Habit
 */
@Entity
@Table(name = "items")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String description;

    // Valeurs possibles LOW, MEDIUM, HIGH
    private String priorite;

    // Valeurs possibles NOT_STARTED, STARTED, DONE
    private String status;

    public Task() {
    }

    public Task(String titre, String description, String priorite, String status) {
        this.titre = titre;
        this.description = description;
        this.priorite = priorite;
        this.status = status;
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

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
