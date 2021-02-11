package main.exercise.utils;

public class StringUtility {

    // Function to check given input is valid
    public static boolean inputValidation(String input) {

        // input constraints regarding to its length
        if (input == null || input.equals("") || input.length() < 1 || input.length() > 300000)
            return false;

        // input constraints regarding to containing characters
        return input.chars().noneMatch(Character::isUpperCase) && input.chars().allMatch(Character::isLetter);

    }
}
