package main.exercise.processor;

import main.exercise.algorithm.data.AlgorithmType;
import main.exercise.exception.AlgorithmTypeInvalidException;
import main.exercise.exception.FilePathInvalidException;

import java.io.File;

public class ProcessorParamsValidator {

    //region Members


    private final String[] vars;

    //endregion

    //region Constructor
    public ProcessorParamsValidator(String... vars) {
        this.vars = vars.clone();
    }
    //endregion

    //region Private Methods
    private String getFilePath() {
        String filepath;
        try {
            filepath = vars[ProcessorConstants.FILEPATH_PARAMETER_INDEX];
            File file = new File(filepath);
            if (!file.exists())
                new File((String) null);
        } catch (Exception e) {
            throw new FilePathInvalidException(e);
        }
        return filepath;
    }

    private AlgorithmType getAlgorithmTypeParameter() {
        try {
            return AlgorithmType.valueOf(vars[ProcessorConstants.ALGORITHM_PARAMETER_INDEX]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return AlgorithmType.arrayset;
        } catch (IllegalArgumentException ex) {
            throw new AlgorithmTypeInvalidException(ex);
        }

    }

    //endregion

    //region Public Methods
    public ProcessorParams getParameters() {
        return ProcessorParams.newInstance(getFilePath(), getAlgorithmTypeParameter());
    }

    //endregion
}
