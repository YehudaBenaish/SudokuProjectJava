package main.java.com.sudokusolver;

import main.java.com.sudokusolver.dm.SudokuPuzzle;
import main.java.com.sudokusolver.service.SudokuService;
import main.java.com.sudokusolver.dao.SudokuDaoImpl;
import main.java.com.sudokusolver.dao.UserDaoImpl;

public class Main {
    public static void main(String[] args) {
        // Example Sudoku board
        int[][] board = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        // Create a SudokuPuzzle instance
        SudokuPuzzle puzzle = new SudokuPuzzle("testUser", board);

        // Create services
        SudokuDaoImpl sudokuDao = new SudokuDaoImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        SudokuService sudokuService = new SudokuService(sudokuDao, userDao);

        // Solve the puzzle
        boolean solved = sudokuService.solvePuzzle(puzzle);
        System.out.println("Puzzle solved: " + solved);
    }
}
