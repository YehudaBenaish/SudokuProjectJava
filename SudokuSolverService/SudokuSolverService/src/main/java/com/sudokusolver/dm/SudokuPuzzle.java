package main.java.com.sudokusolver.dm;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class SudokuPuzzle implements Serializable {
    private String username;
    private int[][] board;

    public SudokuPuzzle() {}

    public SudokuPuzzle(String username, int[][] board) {
        this.username = username;
        this.board = board;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuPuzzle that = (SudokuPuzzle) o;
        return Objects.equals(username, that.username) && Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(username);
        result = 31 * result + Arrays.deepHashCode(board);
        return result;
    }
}
