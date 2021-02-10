package main.exercise.exception;

public class ParameterInvalidException extends RuntimeException {
    public ParameterInvalidException(Exception e) {
        super(e);
    }
}
