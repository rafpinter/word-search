//package org.example;

/**
 * Represents a coordinate position within a two-dimensional grid.
 * This class captures a position through its row and column indices.
 *
 * For example, a Position object with row 2 and column 3 represents the coordinate (2, 3) in the grid.
 */
public class Position {

    // The row index of the position in the grid.
    private final int row;

    // The column index of the position in the grid.
    private final int column;

    /**
     * Initializes a new Position object with specified row and column indices.
     *
     * @param row    The zero-based row index of the position.
     * @param column The zero-based column index of the position.
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Retrieves the row index associated with this position.
     *
     * @return The zero-based row index.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Retrieves the column index associated with this position.
     *
     * @return The zero-based column index.
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Provides a string representation of the position in the format: (row,column).
     *
     * @return A string that represents the position's coordinates.
     */
    public String getPositionString() {
        return "(" + row + "," + column + ")";
    }
}
