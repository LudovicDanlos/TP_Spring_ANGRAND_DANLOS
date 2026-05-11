package org.example.controller;

import org.example.dto.TaskInput;
import org.example.dto.TaskResponse;
import org.example.service.TaskService;
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
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody TaskInput request) {
        // 1. remplacer Object par un vrai DTO d'entree
        // 2. appeler le service
        // 3. renvoyer 201 Created
        TaskResponse response = taskService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id, @RequestBody TaskInput request) {
        // 1. remplacer Object par un vrai DTO d'entree
        // 2. appeler le service
        // 3. renvoyer la bonne reponse HTTP
        TaskResponse response = taskService.update(id, request);

        return ResponseEntity.ok(taskService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // appeler le service puis choisir la bonne reponse HTTP.
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
