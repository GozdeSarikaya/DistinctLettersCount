package main.exercise.processor;

import main.exercise.exception.AlgorithmTypeInvalidException;
import main.exercise.exception.FilePathInvalidException;
import main.exercise.exception.FileProcessException;
import main.exercise.exception.ParameterInvalidException;
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

    //endregion

    //region Constructor

    public Processor() {
        logger = LoggerFactory.getLogger(ProcessorConstants.PROGRAMNAME);
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
                            int count = 0;
                            // calculate minimum deletion of letters with 2 different algorithms
                            if (processorParams.getAlgorithmType() == ProcessorEnum.AlgorithmType.without_pq)
                                count = minDeletions_without_PriorityQueue(line);
                            if (processorParams.getAlgorithmType() == ProcessorEnum.AlgorithmType.with_pq)
                                count = minDeletions_with_PriorityQueue(line);
                            // print out the results
                            if (count >= 0) builder.append(i + " -> " + count + "\n");
                            else builder.append(i + " -> " + ProcessorConstants.INVALIDLINE_KEY + "\n");
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

    // ALGORITHM-1 : Function to find the minimum count of characters required to be deleted to make frequencies of all characters unique
    private int minDeletions_without_PriorityQueue(String input) {

        //Check if input is valid
        if (!inputValidation(input))
            return -1;

        // Stores frequency of each distinct character of string
        int[] letterCounts = new int[26];

        //Count the frequency of all the letters.
        for (char c : input.toCharArray())
            letterCounts[c - 'a']++;

        // Stores frequency of each distinct character of string
        Set<Integer> set = new HashSet<>();

        // Stores minimum count of characters required to be deleted to make frequency of each character unique
        int count = 0;

        // Go through all the frequencies, and add them to a set.
        // If a number already exists in a set, it will return false if you try to add it.
        // If it does return false decrease its frequency, and try and add it again.
        // Repeat this until it gets added, and then move on to the next number.
        for (int c : letterCounts) {
            // Everytime you remove an item increase the count.
            while (c != 0 && !set.add(c--)) {
                count++;
            }
        }

        return count;
    }

    // ALGORITHM-2 :Function to find the minimum count of characters required to be deleted to make frequencies of all characters unique
    private int minDeletions_with_PriorityQueue(String input) {

        //Check if input is valid
        if (!inputValidation(input))
            return -1;

        // Stores frequency of each distinct character of string
        Map<Character, Integer> map = new HashMap<>();
        for (char c : input.toCharArray())
            map.put(c, map.getOrDefault(c, 0) + 1);

        // Stores minimum count of characters required to be deleted to make frequency of each character unique
        int count = 0;

        // Stores frequency of each distinct character such that the largest frequency is present at the top
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((x, y) -> Integer.compare(y, x));

        // put non zero occurrences into the queue
        for (Map.Entry<Character, Integer> entry : map.entrySet())
            priorityQueue.add(entry.getValue());

        // Traverse the priority_queue
        while (!priorityQueue.isEmpty()) {
            // take the biggest frequency of a character
            int most_frequent = priorityQueue.poll();

            // If priorityQueue is empty, return count
            if (priorityQueue.isEmpty())
                return count;

            // If frequent and topmost element of priorityQueue are equal
            if (most_frequent == priorityQueue.peek()) {
                // if this frequency is equal to the next one
                // and bigger than 1 decrease it to 1 and put
                // back to the queue
                if (most_frequent > 1) {
                    priorityQueue.add(most_frequent - 1);
                }
                // Update count
                count++;
            }
            // all frequencies which are bigger than
            // the next one are removed from the queue
            // because they are unique
        }

        return count;

    }

    // Function to check given input is valid
    private boolean inputValidation(String input) {

        // input constraints regarding to its length
        if (input == null || input.equals("") || input.length() < 1 || input.length() > 300000)
            return false;

        // input constraints regarding to containing characters
        return input.chars().noneMatch(Character::isUpperCase) && input.chars().allMatch(Character::isLetter);

    }

    //endregion

}
