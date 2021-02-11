package main.exercise.processor;

import main.exercise.exception.FilePathInvalidException;

import java.io.File;
import java.nio.file.NoSuchFileException;

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
            filepath = vars[ProcessorConstants.FILE_PATH_PARAMETER_INDEX];
            File file = new File(filepath);
            if (!file.exists())
                new File((String) null);
        } catch (Exception e) {
            throw new FilePathInvalidException(e);
        }
        return filepath;
    }

    private int getAlgorithmTypeParameter() {
        int no;
        try {
            no = Integer.parseInt(vars[ProcessorConstants.ALGORITHM_PARAMETER_INDEX]);
            if (no > 2) return ProcessorConstants.DEFAULT_ALGORITHM;
            else return no;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return ProcessorConstants.DEFAULT_ALGORITHM;
        }
    }

    //endregion

    //region Public Methods
    public ProcessorParams getParameters() {
        return ProcessorParams.newInstance(getFilePath(), getAlgorithmTypeParameter());
    }

    //endregion
}
