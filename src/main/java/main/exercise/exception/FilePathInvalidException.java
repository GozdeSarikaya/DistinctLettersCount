package main.exercise.exception;

public class FilePathInvalidException extends RuntimeException {
    public FilePathInvalidException(Exception e) {
        super(e);
    }
}
