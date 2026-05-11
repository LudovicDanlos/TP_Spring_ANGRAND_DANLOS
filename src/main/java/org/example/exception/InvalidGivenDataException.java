package org.example.exception;

public class InvalidGivenDataException extends RuntimeException {

    public InvalidGivenDataException() {
        super("Certaines données sont invalides ou manquantes");
    }
}
