//package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;

/**
 * Represents a character puzzle comprising a table of characters and a list of words.
 * Provides utilities to parse the puzzle data from a BufferedReader,
 * find occurrences of characters, and more.
 */
public class WordPuzzle implements Puzzle {

    private final int nRows;
    private final int nColumns;
    private final String[][] data;
    private final List<String> words;

    public ArrayList<String> paths;

    /**
     * Constructs a new Puzzle instance.
     *
     * @param data  A 2D array representing the character table of the puzzle.
     * @param words A list of words associated with the puzzle.
     */
    public WordPuzzle(String[][] data, List<String> words) {
        this.nRows = data.length;
        this.nColumns = data[0].length;
        this.data = data;
        this.words = words;
        this.paths = new ArrayList<>();
    }

    /**
     * Parses a BufferedReader to extract puzzle data and constructs a Puzzle object.
     *
     * @param reader The BufferedReader containing the puzzle data.
     * @return A Puzzle object or null if the end of file is reached.
     * @throws IOException if an I/O error occurs during reading.
     */
    public static WordPuzzle parseChunk(BufferedReader reader) throws IOException {

        // Read the first line to determine the dimensions of the puzzle
        String line = reader.readLine();
        if (line == null) {
            return null;  // No more data to read
        }

        // Extract number of rows and columns from the line
        String[] dimensions = line.split(" ");
        int nRows = Integer.parseInt(dimensions[0]);
        int nColumns = Integer.parseInt(dimensions[1]);

        // Initialize a 2D array to store the puzzle data
        String[][] data = new String[nRows][nColumns];

        // Read each row of the puzzle and populate the 2D array
        for (int i = 0; i < nRows; i++) {
            line = reader.readLine();
            if (line != null) {
                data[i] = line.split(" ");  // Extract individual pieces of data from the read line
            }
        }

        // Read the next line containing the list of words to search
        line = reader.readLine();
        List<String> words = new ArrayList<>();
        if (line != null) {
            String[] wordsArray = line.split(" ");
            for (String word : wordsArray) {
                words.add(word);  // Populate the list with words
            }
        }

        // Return a new Puzzle object with the parsed data and words
        return new WordPuzzle(data, words);
    }



    /**
     * Returns the list of words associated with the puzzle.
     *
     * @return A list of words.
     */
    public List<String> getWords() {
        return words;
    }


    /**
     * Searches the puzzle data for all occurrences of a specific character.
     *
     * @param character The character to be located in the puzzle.
     * @return A stack containing positions of the specified character.
     */
    public ArrayStack<Position> findAllOccurrencesOfCharacter(String character) {
        ArrayStack<Position> stack = new ArrayStack<>();

        // Loop through each element in the data
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                Position currentPosition = new Position(i, j);
                // Utilize the isCharacterAtPosition method to check character equality
                if (isCharacterAtPosition(currentPosition, character)) {
                    stack.push(currentPosition);
                }
            }
        }

        return stack;
    }


    /**
     * Determines if the character at a specified position matches the provided character.
     *
     * @param position  The position within the puzzle's grid to check.
     * @param character The character for comparison.
     * @return True if the characters match, otherwise false.
     */
    public boolean isCharacterAtPosition(Position position, String character) {
        // Validate the position to ensure it's within the grid boundaries
        if (position.getRow() >= 0 && position.getRow() < nRows &&
                position.getColumn() >= 0 && position.getColumn() < nColumns) {
            // Check and return if the character at the position matches the provided character
            return data[position.getRow()][position.getColumn()].equals(character);
        }
        // If position is out of grid boundaries, return false
        return false;
    }


    /**
     * Finds valid neighboring positions of a given position containing the specified character.
     *
     * @param pos       The starting position for the search.
     * @param character The character to identify valid neighbors.
     * @return A stack containing valid neighboring positions.
     */
    public ArrayStack<Position> findValidNeighbors(Position pos, String character) {
        // Create a stack to hold valid neighbor positions.
        ArrayStack<Position> stack = new ArrayStack<>();

        // Define relative positions of all possible neighbors (self, left, right, up, down, and 4 diagonals).
        // Each array represents the [rowOffset, columnOffset] relative to the original position.
        int[][] neighbors = {
                {0, 0}, {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        // Loop over each possible relative neighbor.
        for(int[] n : neighbors) {
            // Calculate the absolute position of the neighbor by adding the offsets to the original position.
            int newRow = pos.getRow() + n[0];
            int newCol = pos.getColumn() + n[1];

            // Validate if the neighbor is within the puzzle boundaries.
            // - Row should be >= 0 and < total number of rows (nRows).
            // - Column should be >= 0 and < total number of columns (nColumns).
            if(newRow >= 0 && newRow < nRows && newCol >= 0 && newCol < nColumns) {
                // Check if the neighbor contains the desired character.
                // If true, add the neighbor's position to the stack.
                if(isCharacterAtPosition(new Position(newRow, newCol), character)) {
                    stack.push(new Position(newRow, newCol));
                }
            }
        }

        // Return the stack containing all valid neighbors.
        return stack;
    }



    /**
     * Constructs and prints a representation of the specified word using a sequence of positions.
     *
     * @param word           The word to represent.
     * @param positionsDeque The sequence of positions forming the word.
     */
    public void printDequeOfPositions(String word, ArrayDeque<Position> positionsDeque) {
        // Using StringBuilder for efficient string concatenation.
        StringBuilder output = new StringBuilder(word + ": ");

        // Initialize iterator for positionsDeque to sequentially access its items.
        Iterator<Position> iterator = positionsDeque.iterator();

        // Loop through the positions in the deque.
        while (iterator.hasNext()) {
            // Append the string representation of the next position.
            output.append(iterator.next().getPositionString());

            // If there's another position after the current one, append an arrow.
            if (iterator.hasNext()) {
                output.append("->");
            }
        }

        // Print the concatenated string.
//        System.out.println(output.toString());
        // Add the concatenated string to the paths list.
        this.paths.add(output.toString());
    }


    /**
     * Sorts the paths list alphabetically.
     *
     * This method uses Java's built-in Collections.sort() function to
     * arrange the strings inside the paths list in lexicographic order.
     * It modifies the original list, so after calling this method,
     * accessing the paths list will give the sorted paths.
     */
    public void sortPathsAlphabetically() {
        // Use Collections.sort() to sort the paths list in place
        Collections.sort(this.paths);
    }

    /**
     * Prints the paths list in lexicographic order.
     *
     * This method first sorts the paths list alphabetically using
     * the sortPathsAlphabetically function. Then, it iterates over
     * each path and prints it. This ensures that the output is always
     * presented in a sorted manner.
     */
    public void printSortedPaths() {
        // First, sort the paths list alphabetically
        sortPathsAlphabetically();

        // Iterate over each path in the sorted paths list and print it
        for(String path : paths) {
            System.out.println(path);
        }
    }


    /**
     * Checks if the characters at the specified positions form the given word.
     *
     * @param positionsDeque The sequence of positions to be validated.
     * @param word           The target word for validation.
     * @return True if the sequence of characters matches the word, otherwise false.
     */
    public boolean validateWord(ArrayDeque<Position> positionsDeque, String word) {
        StringBuilder constructedWord = new StringBuilder();

        // Clone the positionsDeque to avoid altering the original while processing
        ArrayDeque<Position> positionsDequeCopy = positionsDeque.clone();

        // Temporary stack to retain the original order of positionsDeque
        ArrayStack<Position> tempStack = new ArrayStack<>();

        // Process each position from the deque to construct the word
        while (!positionsDeque.isEmpty()) {
            Position pos = positionsDeque.removeFirst();
            String ch = data[pos.getRow()][pos.getColumn()];
            constructedWord.append(ch);

            // Push position to temp stack to restore order later
            tempStack.push(pos);
        }

        // Restore the order in the original positionsDeque
        while (!tempStack.isEmpty()) {
            positionsDeque.addFirst(tempStack.pop());
        }

        // If the constructed word matches the provided word, print the sequence of positions
        if (constructedWord.toString().equals(word)) {
            printDequeOfPositions(word, positionsDequeCopy);
        }

        // Return whether the constructed word matches the provided word
        return constructedWord.toString().equals(word);
    }



    /**
     * Computes possible sequences of positions to form the target word.
     *
     * @param positionsDeque A deque containing the current sequence of positions.
     * @param targetWord     The word to be formed.
     * @param i              The current character index of the target word being processed.
     */
    public void computeTreeOfWords(ArrayDeque<Position> positionsDeque, String targetWord, int i) {

        // Check if the current sequence of positions forms the target word
        if (positionsDeque.size() == targetWord.length()) {
            boolean is_valid = validateWord(positionsDeque, targetWord);
            // Note: The is_valid variable is computed, but not used in this snippet.
        }

        // If we've processed each character in the target word, end the recursive call
        if (i == targetWord.length()) {
            return;
        }

        // Search for valid neighboring positions that match the current character of the target word
        char character = targetWord.charAt(i);
        ArrayStack<Position> neighbors = findValidNeighbors(positionsDeque.getLast(), "" + character);

        // If no valid neighbors are found, end the recursive call
        if (neighbors.isEmpty()) {
            return;
        }

        // For each valid neighbor, recursively try to form the remainder of the target word
        while (!neighbors.isEmpty()) {
            positionsDeque.addLast(neighbors.pop());
            computeTreeOfWords(positionsDeque, targetWord, i + 1);
            positionsDeque.removeLast();
        }
    }
    }