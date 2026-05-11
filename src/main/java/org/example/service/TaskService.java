package org.example.service;

import org.example.dto.TaskInput;
import org.example.dto.TaskResponse;

import java.util.List;

/**
 * Contrat du service metier.
 * Une seule methode est vraiment implementee dans le squelette.
 * Les autres servent de guide pour le TD / TP.
 */
public interface TaskService {

    List<TaskResponse> findAll();

    TaskResponse findById(Long id);

    TaskResponse create(TaskInput request);

    TaskResponse update(Long id, TaskInput request);

    void delete(Long id);
}
