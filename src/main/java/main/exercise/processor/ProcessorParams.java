package main.exercise.processor;

import main.exercise.algorithm.data.AlgorithmType;

public class ProcessorParams {

    //region Members
    private final String filePath;
    private final AlgorithmType algorithmType;
    //endregion

    //region Constructor
    private ProcessorParams(String filePath, AlgorithmType algorithmType) {
        this.filePath = filePath;
        this.algorithmType = algorithmType;
    }

    public static ProcessorParams newInstance(String filePath, AlgorithmType algorithmType) {
        return new ProcessorParams(filePath, algorithmType);
    }
    //endregion

    //region Getters
    public String getFilePath() {
        return filePath;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }
    //endregion
}
