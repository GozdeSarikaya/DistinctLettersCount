package main.exercise;

import main.exercise.processor.Processor;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        // For simplicity, we pass all arguments to newly created Processor class
        // It will validate the parameters and process each line of the given file.
        String result = new Processor().run(args);
        System.out.println(result);

    }

}
