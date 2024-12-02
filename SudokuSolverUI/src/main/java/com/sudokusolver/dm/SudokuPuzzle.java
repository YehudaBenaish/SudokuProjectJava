package com.sudokusolver.dm;

import java.io.Serializable;

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
}
