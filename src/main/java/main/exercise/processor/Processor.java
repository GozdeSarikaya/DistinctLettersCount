package main.exercise.processor;

import main.exercise.exception.FilePathInvalidException;
import main.exercise.exception.FileProcessException;
import main.exercise.exception.ParameterInvalidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class Processor {

    //region Members

    private final Logger logger;

    //endregion

    //region Constructor

    public Processor() {
        logger = LoggerFactory.getLogger("DistinctLettersCount");
    }

    //endregion

    //region Main Method

    public void run(String... args) throws IOException, FileProcessException, FilePathInvalidException, ParameterInvalidException {

        try {
            logger.info("Application started...");
            ProcessorParams params = new ProcessorParamsValidator(args).getEventParameters();
            logger.debug("Application parameters validated! FilePath: " + params.getFilePath() + ", NumOfThreads: " + params.getNumberOfThreads());
            logger.debug("Application is processing log file...");
            ProcessLogFile(params);
            logger.info("Application finished...");

        } catch (FileProcessException ex) {
            logger.error("File could not be processed!");
            throw new FileProcessException(ex);
        } catch (FilePathInvalidException ex) {
            logger.error("The given path parameter is invalid!");
            throw new FilePathInvalidException(ex);
        } catch (ParameterInvalidException ex) {
            logger.error("Application parameters are invalid! Exception: " + ex);
            throw new ParameterInvalidException(ex);
        }
    }

    //endregion

    //region Private Methods

    private void ProcessLogFile(ProcessorParams processorParams) throws FileProcessException, FilePathInvalidException, IOException {

        try {
            logger.debug("Log file processing step is started...");

            try (Stream<String> stream = Files.lines(Paths.get(processorParams.getFilePath()))) {
                AtomicInteger i = new AtomicInteger(1);
                stream
                        .forEach(line -> {
                            int count = solution(line);
                            if (count >= 0) System.out.println(i + " -> " + count);
                            else System.out.println(i + " -> INVALID");
                            i.addAndGet(1);
                        });
            }

            logger.debug("Log file processing step is finished...");

        } catch (NullPointerException | NoSuchFileException | FileNotFoundException ex) {
            throw new FilePathInvalidException(ex);
        } catch (Exception ex) {
            throw new FileProcessException(ex);
        }


    }

    private static int solution(String S) {

        if (S == null || S.equals("") || S.length() > 300000)
            return -1;

        Pattern p = Pattern.compile("([0-9])");
        Matcher m = p.matcher(S);
        if (m.find())
            return -1;

        if (S.chars().anyMatch(Character::isUpperCase) || S.chars().noneMatch(Character::isLetter))
            return -1;


        Map<Character, Integer> map = new HashMap<>();
        for (char c : S.toCharArray())
            map.put(c, map.getOrDefault(c, 0) + 1);


        int count = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        // put non zero occurrences into the queue
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            pq.add(entry.getValue());
        }

        while (pq.size() != 0) {
            // take the biggest frequency of a character
            int most_frequent = pq.poll();
            if (pq.size() == 0) {
                return count;
            }

            if (most_frequent == pq.peek()) {
                // if this frequency is equal to the next one
                // and bigger than 1 decrease it to 1 and put
                // back to the queue
                if (most_frequent > 1) {
                    pq.add(most_frequent - 1);
                }
                count++;
            }
            // all frequencies which are bigger than
            // the next one are removed from the queue
            // because they are unique
        }

        return count;

    }

    //endregion

}
