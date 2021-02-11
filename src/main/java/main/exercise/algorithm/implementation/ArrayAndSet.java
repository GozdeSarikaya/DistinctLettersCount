package main.exercise.algorithm.implementation;

import main.exercise.algorithm.base.IProcessAlgorithm;

import java.util.HashSet;
import java.util.Set;

// ALGORITHM-1 : Function to find the minimum count of characters required to be deleted to make frequencies of all characters unique
public class ArrayAndSet implements IProcessAlgorithm {

    @Override
    public int minDeletions(String input) {

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
}
