package main.exercise.processor;

public class ProcessorParams {

    //region Members
    private final String filePath;
    private final int algorithmType;
    //endregion

    //region Constructor
    private ProcessorParams(String filePath, int algorithmType) {
        this.filePath = filePath;
        this.algorithmType = algorithmType;
    }

    public static ProcessorParams newInstance(String filePath, int algorithmType) {
        return new ProcessorParams(filePath, algorithmType);
    }
    //endregion

    //region Getters
    public String getFilePath() {
        return filePath;
    }

    public int getAlgorithmType() {
        return algorithmType;
    }
    //endregion
}
