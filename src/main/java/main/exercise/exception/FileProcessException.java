package main.exercise.exception;

public class FileProcessException extends RuntimeException {
    public FileProcessException(Exception e) {
        super(e);
    }
}
