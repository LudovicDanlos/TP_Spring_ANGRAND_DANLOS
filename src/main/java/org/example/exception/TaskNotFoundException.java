package org.example.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(int id) {
        super("La tâche d'id " + id + " n'existe pas");
    }
}
