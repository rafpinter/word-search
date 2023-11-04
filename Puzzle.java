//package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;

public interface Puzzle {


    /**
     * Returns the list of words associated with the puzzle.
     * @return A list of words.
     */
    List<String> getWords();

    /**
     * Searches the puzzle data for all occurrences of a specific character.
     * @param character The character to be located in the puzzle.
     * @return A stack containing positions of the specified character.
     */
    ArrayStack<Position> findAllOccurrencesOfCharacter(String character);

    /**
     * Determines if the character at a specified position matches the provided character.
     * @param position  The position within the puzzle's grid to check.
     * @param character The character for comparison.
     * @return True if the characters match, otherwise false.
     */
    boolean isCharacterAtPosition(Position position, String character);

    /**
     * Finds valid neighboring positions of a given position containing the specified character.
     * @param pos       The starting position for the search.
     * @param character The character to identify valid neighbors.
     * @return A stack containing valid neighboring positions.
     */
    ArrayStack<Position> findValidNeighbors(Position pos, String character);

    /**
     * Constructs and prints a representation of the specified word using a sequence of positions.
     * @param word           The word to represent.
     * @param positionsDeque The sequence of positions forming the word.
     */
    void printDequeOfPositions(String word, ArrayDeque<Position> positionsDeque);

    /**
     * Checks if the characters at the specified positions form the given word.
     * @param positionsDeque The sequence of positions to be validated.
     * @param word           The target word for validation.
     * @return True if the sequence of characters matches the word, otherwise false.
     */
    boolean validateWord(ArrayDeque<Position> positionsDeque, String word);

    /**
     * Computes possible sequences of positions to form the target word.
     * @param positionsDeque A deque containing the current sequence of positions.
     * @param targetWord     The word to be formed.
     * @param i              The current character index of the target word being processed.
     */
    void computeTreeOfWords(ArrayDeque<Position> positionsDeque, String targetWord, int i);
}
