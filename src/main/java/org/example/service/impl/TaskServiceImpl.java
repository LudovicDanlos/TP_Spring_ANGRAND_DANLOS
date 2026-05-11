package org.example.service.impl;

import org.example.dto.TaskInput;
import org.example.dto.TaskResponse;
import org.example.entity.Task;
import org.example.repository.TaskRepository;
import org.example.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Ici se trouve la logique metier.
 *
 * Le squelette ne laisse qu'un seul vrai exemple fonctionnel : findAll().
 * Le reste doit etre complete par les eleves.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskResponse> findAll() {
        // Exemple complet :
        // on lit les donnees depuis le repository
        // puis on transforme les entites en DTO de sortie.
        return taskRepository.findAll()
                .stream()
                .map(TaskResponse::fromEntity)
                .toList(); // Ici le stream peut être remplacé par plein d'autre moyen. Il sagit juste d'une manière efficace et efficiente d'écrire cette partie.

        /* Autre manière de faire si le stream n'est pas familié
        List<ItemResponse> itemResponses = new ArrayList<>();
        List<Item> all = itemRepository.findAll();

        for (Item item : all) {
            ItemResponse itemResponse = new ItemResponse(item.getId(), item.getName(), item.getDescription(), item.isDone());
            itemResponses.add(itemResponse);
        }
        return itemResponses;
         */
    }

    @Override
    public TaskResponse findById(Long id) {
        // 1. lire l'item depuis le repository
        // 2. gerer le cas ou l'item n'existe pas
        // 3. renvoyer un ItemResponse
        Optional<Task> itemOptional = taskRepository.findById(id);

        if (itemOptional.isEmpty()) {
            return null;
        } else  {
            Task task = itemOptional.get();
            return new TaskResponse(
                    task.getId(),
                    task.getName(),
                    task.getDescription(),
                    task.getPriority(),
                    task.getStatus(),
                    task.getCreationDate(),
                    task.getDeadline()
            );
        }
    }

    @Override
    public TaskResponse create(TaskInput request) {
        // 1. remplacer Object par un vrai DTO d'entree
        // 2. appliquer les regles metier
        // 3. creer l'entite
        // 4. sauvegarder avec le repository
        // 5. renvoyer un ItemResponse
        Task task = new Task(
                request.getName(),
                request.getDescription(),
                request.getPriority(),
                request.getStatus(),
                request.getCreationDate(),
                request.getDeadline()
        );

        return TaskResponse.fromEntity(taskRepository.save(task));
    }

    @Override
    public TaskResponse update(Long id, TaskInput request) {
        // 1. remplacer Object par un vrai DTO d'entree
        // 2. retrouver l'item en base
        // 3. modifier les champs utiles
        // 4. sauvegarder
        // 5. renvoyer un ItemResponse
        Optional<Task> itemOptional = taskRepository.findById(id);

        if (itemOptional.isEmpty()) {
            return null;
        } else  {
            Task task = itemOptional.get();
            task.setName(request.getName());
            task.setDescription(request.getDescription());
            task.setStatus(request.getStatus());
            taskRepository.save(task);
            return new TaskResponse(
                    id,
                    request.getName(),
                    request.getDescription(),
                    request.getPriority(),
                    request.getStatus(),
                    request.getCreationDate(),
                    request.getDeadline()
            );
        }
    }

    @Override
    public void delete(Long id) {
        // 1. retrouver l'item
        // 2. le supprimer
        // 3. reflechir au comportement si l'id n'existe pas
        Optional<Task> itemOptional = taskRepository.findById(id);

        itemOptional.ifPresent(taskRepository::delete);
    }
}
