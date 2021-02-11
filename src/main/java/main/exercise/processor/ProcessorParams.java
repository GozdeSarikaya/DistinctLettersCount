package main.exercise.processor;

public class ProcessorParams {

    //region Members
    private final String filePath;
    private final ProcessorEnum.AlgorithmType algorithmType;
    //endregion

    //region Constructor
    private ProcessorParams(String filePath, ProcessorEnum.AlgorithmType algorithmType) {
        this.filePath = filePath;
        this.algorithmType = algorithmType;
    }

    public static ProcessorParams newInstance(String filePath, ProcessorEnum.AlgorithmType algorithmType) {
        return new ProcessorParams(filePath, algorithmType);
    }
    //endregion

    //region Getters
    public String getFilePath() {
        return filePath;
    }

    public ProcessorEnum.AlgorithmType getAlgorithmType() {
        return algorithmType;
    }
    //endregion
}
