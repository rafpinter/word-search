//package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;

/**
 * The Main class is the entry point for the word puzzle search program.
 * The program takes in a file path as a command line argument,
 * reads the content of the file, and for each puzzle specified in the file,
 * it looks for the words specified and their occurrence patterns.
 */
public class Main {

    /**
     * Entry point for the application.
     *
     * @param args Command line arguments. Expects the first argument to be the path to the puzzle file.
     */
    public static void main(String[] args) {

        // Check if file path is provided as command line argument
        if (args.length < 1) {
            System.out.println("Please provide the file path as a command line argument.");
            return;
        }

        String filePath = args[0];

        // Attempt to read the provided file and process the puzzles
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            WordPuzzle wordPuzzle;
            int puzzleCounter = 1; // Counter to keep track of the number of puzzles processed

            // Process each chunk of data in the file. Each chunk represents a puzzle.
            while ((wordPuzzle = WordPuzzle.parseChunk(reader)) != null) {

                System.out.println("Query " + puzzleCounter + ":");

                // For each word in the current puzzle, try to find its occurrence pattern
                for (String word : wordPuzzle.getWords()) {

                    // Determine the first character of the current word
                    String firstCharacter = String.valueOf(word.charAt(0));

                    // Find all positions of the first character of the word in the puzzle
                    ArrayStack<Position> firstCharacterPositions = wordPuzzle.findAllOccurrencesOfCharacter(firstCharacter);

                    // For each position of the first character, try to determine the pattern of the word
                    while (!firstCharacterPositions.isEmpty()) {

                        // Get the next position of the first character
                        Position firstPosition = firstCharacterPositions.pop();

                        // Initialize a position deque with the found position as the starting point
                        ArrayDeque<Position> positions = new ArrayDeque<>();
                        positions.addFirst(firstPosition);

                        // Attempt to find the pattern of the word starting from the current position of its first character
                        for (int i = 1; i < word.length(); i++) {
                            wordPuzzle.computeTreeOfWords(positions, word, i);
                        }
                    }
                }

                wordPuzzle.printSortedPaths(); // Printing sorted paths

                puzzleCounter++; // Move to the next puzzle
            }

        } catch (IOException e) {  // Catch any IO exceptions and print the stack trace for debugging
            e.printStackTrace();
        }
    }
}
