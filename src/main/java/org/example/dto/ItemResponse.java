package org.example.dto;

import org.example.entity.Item;

/**
 * DTO de sortie.
 * Permet de controler ce que l'API renvoie.
 */
public class ItemResponse {

    private Long id;
    private String name;
    private String description;
    private boolean done;

    public ItemResponse() {
    }

    public ItemResponse(Long id, String name, String description, boolean done) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.done = done;
    }

    public static ItemResponse fromEntity(Item item) {
        return new ItemResponse(
            item.getId(),
            item.getName(),
            item.getDescription(),
            item.isDone()
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
