package org.example.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(long id) {
        super("La tâche d'id " + id + " n'existe pas");
    }
}
