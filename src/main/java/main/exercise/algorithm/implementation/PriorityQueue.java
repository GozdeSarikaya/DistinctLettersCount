package main.exercise.algorithm.implementation;

import main.exercise.algorithm.base.IProcessAlgorithm;

import java.util.HashMap;
import java.util.Map;

// ALGORITHM-2 :Function to find the minimum count of characters required to be deleted to make frequencies of all characters unique
public class PriorityQueue implements IProcessAlgorithm {

    @Override
    public int minDeletions(String input) {

        // Stores frequency of each distinct character of string
        Map<Character, Integer> map = new HashMap<>();
        for (char c : input.toCharArray())
            map.put(c, map.getOrDefault(c, 0) + 1);

        // Stores minimum count of characters required to be deleted to make frequency of each character unique
        int count = 0;

        // Stores frequency of each distinct character such that the largest frequency is present at the top
        java.util.PriorityQueue<Integer> priorityQueue = new java.util.PriorityQueue<>((x, y) -> Integer.compare(y, x));

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
}
