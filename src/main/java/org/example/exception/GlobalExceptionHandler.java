package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * L'utilisation de @RestControllerAdvice est une bonne pratique (bravo)
 * et va dans le sens d'une gestion centralisée des erreurs.
 *
 * En revanche, les handlers renvoient uniquement une String.
 * Pour le TP, cela reste fonctionnel et les statuts HTTP sont justes
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFound(TaskNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(WrongValueException.class)
    public ResponseEntity<String> handleWrongValue(WrongValueException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
