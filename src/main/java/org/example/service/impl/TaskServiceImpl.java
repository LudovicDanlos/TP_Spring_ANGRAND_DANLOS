package org.example.service.impl;

import org.example.dto.TaskInput;
import org.example.dto.TaskResponse;
import org.example.entity.Task;
import org.example.exception.TaskNotFoundException;
import org.example.exception.WrongValueException;
import org.example.repository.TaskRepository;
import org.example.service.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskResponse> findAll(int page, int size) {
        // Exemple complet :
        // on lit les donnees depuis le repository
        // puis on transforme les entites en DTO de sortie.
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findAll(pageable)
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Override
    public List<TaskResponse> findByStatus(String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findByStatus(pageable, status)
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Override
    public List<TaskResponse> findByPriority(String priority, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findByPriority(pageable, priority)
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Override
    public List<TaskResponse> findByStatusAndPriority(String status, String priority, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findByStatusAndPriority(pageable, status, priority)
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Override
    public TaskResponse findById(Long id) {
        // 1. lire l'item depuis le repository
        // 2. gerer le cas ou l'item n'existe pas
        // 3. renvoyer un ItemResponse
        Optional<Task> itemOptional = taskRepository.findById(id);

        if (itemOptional.isEmpty()) {
            throw new TaskNotFoundException(id);
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
    /**
     * La validation des regles metier dans le service est conforme au sujet
     *
     * En revanche, la creation de la date courante est inutilement complexe :
     * LocalDateTime.now() suffisait. Ce n'est pas bloquant, donc pas de retrait,
     * mais cela ajoute du bruit inutile.
     */
    public TaskResponse create(TaskInput request) {
        // 1. remplacer Object par un vrai DTO d'entree
        // 2. appliquer les regles metier
        // 3. creer l'entite
        // 4. sauvegarder avec le repository
        // 5. renvoyer un ItemResponse

        // Verification de la validite de la tache entree
        isTaskInputValid(request);

        Task task = new Task(
                request.getName(),
                request.getDescription(),
                request.getPriority(),
                request.getStatus(),
                LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter), // Temps actuel
                request.getDeadline()
        );

        return TaskResponse.fromEntity(taskRepository.save(task));
    }

    @Override
    /**
     * La methode verifie bien l'existence de la tache avant ecriture,
     * ce qui est demande par le sujet.
     *
     * En revanche, la reponse est reconstruite a partir du DTO d'entree
     * au lieu de partir de l'entite reellement sauvegardee.
     * Cela peut renvoyer un etat incoherent a l'appelant, notamment pour
     * les champs geres par le backend. (-0.5)
     */
    public TaskResponse update(Long id, TaskInput request) {
        // 1. remplacer Object par un vrai DTO d'entree
        // 2. retrouver l'item en base
        // 3. modifier les champs utiles
        // 4. sauvegarder
        // 5. renvoyer un ItemResponse

        // Verification de la validite de la tache entree
        isTaskInputValid(request);

        Optional<Task> itemOptional = taskRepository.findById(id);

        if (itemOptional.isEmpty()) {
            throw new TaskNotFoundException(id);
        } else  {
            Task task = itemOptional.get();
            task.setName(request.getName());
            task.setDescription(request.getDescription());
            task.setPriority(request.getPriority());
            task.setStatus(request.getStatus());
            task.setDeadline(request.getDeadline());
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

        if (itemOptional.isEmpty()) {
            throw new TaskNotFoundException(id);
        }

        itemOptional.ifPresent(taskRepository::delete);
    }

    //////////////////////////////// Methodes utilitaires ///////////////////////////////////////

    /**
     * Cette centralisation des regles est acceptable pour le TP :
     * le sujet demande explicitement que le service contienne la logique metier
     * et verifie les regles.
     */
    public static void isTaskInputValid(TaskInput taskInput){
        isTitleValid(taskInput.getName());
        isPriorityValid(taskInput.getPriority());
        isStatusValid(taskInput.getStatus());
        isDeadlineValid(taskInput.getDeadline());
    }

    /**
     * La regle "titre obligatoire" est bien presente, ce qui est attendu.
     *
     * En revanche, title n'est pas teste contre null avant l'appel a isEmpty().
     * Dans ce cas, on ne renvoie plus une erreur metier claire mais un plantage
     * technique possible en 500. (-0.5)
     */
    public static void isTitleValid(String title){

        if (title.isEmpty()){
            throw new WrongValueException("La valeur de l'attribut 'title' est obligatoire");
        }
    }

    /**
     * La regle "priorite obligatoire et bornee a des valeurs attendues"
     * est bien implementee, donc le fond est bon.
     *
     * En revanche, priority n'est pas teste contre null avant isEmpty().
     * Meme consequence que precedemment : risque de NullPointerException
     * au lieu d'une erreur claire retournee a l'utilisateur.
     */
    public static void isPriorityValid(String priority){
        boolean priorityValid = false;

        if (!priority.isEmpty()){
            if ((priority.compareTo("LOW") == 0) || (priority.compareTo("MEDIUM") == 0) || (priority.compareTo("HIGH") == 0)){
                priorityValid = true;
            }
        }

        if (!priorityValid){
            throw new WrongValueException("La valeur de l'attribut 'priority' est obligatoire et ne peut etre autre que : LOW, MEDIUM ou HIGH");
        }
    }

    /**
     * La regle sur le statut est bien presente et conforme au sujet.
     *
     * En revanche, status n'est pas teste contre null avant isEmpty().
     * On retrouve le meme probleme de robustesse que sur les autres validations :
     * une entree invalide peut provoquer une erreur technique au lieu d'une
     * erreur metier propre.
     */
    public static void isStatusValid(String status){
        boolean statusValid = false;

        if (!status.isEmpty()){
            if ((status.compareTo("TODO") == 0) || (status.compareTo("IN_PROGRESS") == 0) || (status.compareTo("DONE") == 0)){
                statusValid = true;
            }
        }

        if (!statusValid){
            throw new WrongValueException("La valeur de l'attribut 'status' est obligatoire et ne peut etre autre que : TODO, IN_PROGRESS ou DONE");
        }
    }

    /**
     * La verification d'une deadline future est une bonne regle metier.
     * Rien a retirer ici sur le fond.
     */
    public static void isDeadlineValid(LocalDateTime deadline){
        boolean deadlineValid = false;

        if (deadline != null){
            if (deadline.isAfter(LocalDateTime.now())){
                deadlineValid = true;
            }
        }

        if (!deadlineValid){
            throw new WrongValueException("La valeur de l'attribut 'deadline' est obligatoire et doit etre une date future a la date actuelle");
        }
    }
}
