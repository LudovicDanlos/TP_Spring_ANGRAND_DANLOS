package org.example.controller;

import org.example.dto.ItemInput;
import org.example.dto.ItemResponse;
import org.example.entity.Item;
import org.example.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Le controller parle HTTP.
 * Il ne doit pas contenir la logique metier.
 *
 * Le seul endpoint complet du squelette est GET /items.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> findAll() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ItemResponse> create(@RequestBody ItemInput request) {
        // 1. remplacer Object par un vrai DTO d'entree
        // 2. appeler le service
        // 3. renvoyer 201 Created
        ItemResponse response = itemService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> update(@PathVariable Long id, @RequestBody ItemInput request) {
        // 1. remplacer Object par un vrai DTO d'entree
        // 2. appeler le service
        // 3. renvoyer la bonne reponse HTTP
        ItemResponse response = itemService.update(id, request);

        return ResponseEntity.ok(itemService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // appeler le service puis choisir la bonne reponse HTTP.
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
