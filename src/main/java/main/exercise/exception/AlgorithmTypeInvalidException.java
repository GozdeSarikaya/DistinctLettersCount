package main.exercise.exception;

public class AlgorithmTypeInvalidException extends RuntimeException {
    public AlgorithmTypeInvalidException(Exception e) {
        super(e);
    }
}
