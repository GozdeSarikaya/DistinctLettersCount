package main.exercise.processor;

import main.exercise.algorithm.base.AlgorithmFactory;
import main.exercise.algorithm.base.IProcessAlgorithm;
import main.exercise.exception.AlgorithmTypeInvalidException;
import main.exercise.exception.FilePathInvalidException;
import main.exercise.exception.FileProcessException;
import main.exercise.exception.ParameterInvalidException;
import main.exercise.utils.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


public class Processor {

    //region Members

    private final Logger logger;
    private final AlgorithmFactory algorithmFactory;
    private IProcessAlgorithm algorithm;
    //endregion

    //region Constructor

    public Processor() {
        logger = LoggerFactory.getLogger(ProcessorConstants.PROGRAMNAME);
        algorithmFactory = new AlgorithmFactory();
    }

    //endregion

    //region Public Methods

    //Main processing function
    public String run(String... args) {

        String result = "";
        try {
            logger.debug("Application started...");
            // validates given parameters
            ProcessorParams params = new ProcessorParamsValidator(args).getParameters();
            logger.debug("Application parameters validated! FilePath: " + params.getFilePath() + ", AlgorithmType: " + params.getAlgorithmType());
            logger.debug("Application is processing log file...");
            // get the algorithm by using algorithm type parameter
            algorithm = algorithmFactory.getAlgorithm(params.getAlgorithmType());
            // read and process all lines of given file
            result = processLogFile(params);
            logger.debug("Application finished...");

        } catch (FileProcessException ex) {
            logger.error("File could not be processed!");
            throw new FileProcessException(ex);
        } catch (FilePathInvalidException ex) {
            logger.error("The given path parameter is invalid!");
            throw new FilePathInvalidException(ex);
        } catch (AlgorithmTypeInvalidException ex) {
            logger.error("The given algorithm parameter is invalid! Please write either `without_pq` or `with_pq` to choose an algorithm.");
            throw new AlgorithmTypeInvalidException(ex);
        } catch (ParameterInvalidException ex) {
            logger.error("Application parameters are invalid! Exception: " + ex);
            throw new ParameterInvalidException(ex);
        }
        return result;
    }

    //endregion

    //region Private Methods

    // Function to process file with given command line parameters
    private String processLogFile(ProcessorParams processorParams) {

        String result = "";
        try {
            logger.debug("Log file processing step is started...");
            StringBuilder builder = new StringBuilder();
            // read all files with given path parameter
            try (Stream<String> stream = Files.lines(Paths.get(processorParams.getFilePath()))) {
                // stores line number
                AtomicInteger i = new AtomicInteger(1);
                stream
                        .forEach(line -> {
                            int count;
                            if (!StringUtility.inputValidation(line))
                                count = -1;
                            else
                                count = algorithm.minDeletions(line);

                            // print out the results
                            if (count >= 0) builder.append(i).append(" -> ").append(count).append("\n");
                            else builder.append(i).append(" -> ").append(ProcessorConstants.INVALIDLINE_KEY).append("\n");
                            // increase line count
                            i.addAndGet(1);
                        });

                result = builder.toString();
            }

            logger.debug("Log file processing step is finished...");

        } catch (NullPointerException | NoSuchFileException | FileNotFoundException ex) {
            throw new FilePathInvalidException(ex);
        } catch (Exception ex) {
            throw new FileProcessException(ex);
        }

        return result;
    }

    //endregion

}
