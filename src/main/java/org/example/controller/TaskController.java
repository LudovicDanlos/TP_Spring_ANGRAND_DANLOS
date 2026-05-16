package org.example.controller;

import org.example.dto.TaskInput;
import org.example.dto.TaskResponse;
import org.example.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Le controller parle HTTP.
 * Il ne doit pas contenir la logique metier.
 *
 * Le seul endpoint complet du squelette est GET /items.
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    /**
     * Il y a ici une logique de selection selon les query params directement
     * dans le controller.
     * (-2)
     * A noter aussi : la solution ne serait pas une simple surcharge Java,
     * mais eventuellement des mappings Spring differents avec params = ...
     */
    public ResponseEntity<List<TaskResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "999") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority) {

        if (status != null && !status.isEmpty() && priority != null && !priority.isEmpty()) {
            return ResponseEntity.ok(taskService.findByStatusAndPriority(status, priority, page, size));
        } else if (status != null && !status.isEmpty()) {
            return ResponseEntity.ok(taskService.findByStatus(status, page, size));
        } else if (priority != null && !priority.isEmpty()) {
            return ResponseEntity.ok(taskService.findByPriority(priority, page, size));
        }
        return ResponseEntity.ok(taskService.findAll(page, size));
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
    /**
     * La reponse HTTP choisie est correcte, mais cette methode appelle le service
     * update(...) puis relit immediatement la tache avec findById(...).
     * Cela fait un appel supplementaire inutile alors que update renvoie deja
     * un TaskResponse.(-0.5)
     *
     * La variable response n'est en plus pas utilisee.
     */
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
