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
import java.util.*;
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
        logger = LoggerFactory.getLogger(ProcessorConstants.PROGRAMNAME);
    }

    //endregion

    //region Main Method

    public void run(String... args) {

        try {
            logger.info("Application started...");
            ProcessorParams params = new ProcessorParamsValidator(args).getParameters();
            logger.debug("Application parameters validated! FilePath: " + params.getFilePath() + ", AlgorithmType: " + params.getAlgorithmType());
            logger.debug("Application is processing log file...");
            processLogFile(params);
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

    private void processLogFile(ProcessorParams processorParams) {

        try {
            logger.debug("Log file processing step is started...");

            try (Stream<String> stream = Files.lines(Paths.get(processorParams.getFilePath()))) {
                AtomicInteger i = new AtomicInteger(1);
                stream
                        .forEach(line -> {
                            int count = 0;
                            if (processorParams.getAlgorithmType() == 1)
                                count = minDeletions_without_PriorityQueue(line);
                            if (processorParams.getAlgorithmType() == 2)
                                count = minDeletions_with_PriorityQueue(line);
                            if (count >= 0) System.out.println(i + " -> " + count);
                            else System.out.println(i + " -> " + ProcessorConstants.INVALIDLINE_KEY);
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

    public int minDeletions_without_PriorityQueue(String s) {

        //Check if input is valid
        if (!inputValidation(s))
            return -1;

        // Stores frequency of each distinct character of string
        int[] letterCounts = new int[26];

        //Count the frequency of all the letters.
        for (char c : s.toCharArray())
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


    // Function to find the minimum count of characters required to be deleted to make frequencies of all characters unique
    private static int minDeletions_with_PriorityQueue(String input) {

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
        PriorityQueue<Integer> pq = new PriorityQueue<>((x, y) -> Integer.compare(y, x));

        // put non zero occurrences into the queue
        for (Map.Entry<Character, Integer> entry : map.entrySet())
            pq.add(entry.getValue());

        // Traverse the priority_queue
        while (!pq.isEmpty()) {
            // take the biggest frequency of a character
            int most_frequent = pq.poll();

            // If pq is empty, return count
            if (pq.isEmpty())
                return count;

            // If frequent and topmost element of pq are equal
            if (most_frequent == pq.peek()) {
                // if this frequency is equal to the next one
                // and bigger than 1 decrease it to 1 and put
                // back to the queue
                if (most_frequent > 1) {
                    pq.add(most_frequent - 1);
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

    private static boolean inputValidation(String input) {

        if (input == null || input.equals("") || input.length() > 300000)
            return false;

        Pattern p = Pattern.compile("([0-9])");
        Matcher m = p.matcher(input);
        if (m.find())
            return false;

        return input.chars().noneMatch(Character::isUpperCase) && input.chars().anyMatch(Character::isLetter);

    }

    //endregion

}
