package main.exercise.algorithm.base;

import main.exercise.algorithm.data.AlgorithmType;
import main.exercise.algorithm.implementation.ArrayAndSet;
import main.exercise.algorithm.implementation.PriorityQueue;

public class AlgorithmFactory {

    public IProcessAlgorithm getAlgorithm(AlgorithmType algorithmType) {

        switch (algorithmType) {
            case arrayset:
                return new ArrayAndSet();
            case priorityqueue:
                return new PriorityQueue();
            default:
                return new ArrayAndSet();
        }
    }
}
