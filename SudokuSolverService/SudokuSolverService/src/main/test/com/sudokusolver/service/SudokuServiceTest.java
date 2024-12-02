package main.test.com.sudokusolver.service;

import main.java.com.sudokusolver.dm.SudokuPuzzle;
import main.java.com.sudokusolver.dao.SudokuDaoImpl;
import main.java.com.sudokusolver.dao.UserDaoImpl;
import main.java.com.sudokusolver.service.SudokuService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class SudokuServiceTest {
    private SudokuService sudokuService;

    @Before
    public void setUp() {
        sudokuService = new SudokuService(new SudokuDaoImpl(), new UserDaoImpl());
    }

    @Test
    public void testSolveAndSavePuzzle() {
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
        SudokuPuzzle puzzle = new SudokuPuzzle("testUser", board);
        boolean result = sudokuService.solvePuzzle(puzzle);
        assertTrue("The puzzle should be solved successfully", result);

        // Debugging: Print the solved puzzle
        System.out.println("Solved Puzzle:");
        for (int[] row : puzzle.getBoard()) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }

        // Verify that the puzzle is saved correctly
        List<SudokuPuzzle> puzzles = sudokuService.getLastFivePuzzles("testUser");
        assertTrue("The solved puzzle should be saved", puzzles.contains(puzzle));
    }

    @Test
    public void testGetLastFivePuzzles() {
        String username = "testUser";
        List<SudokuPuzzle> puzzles = sudokuService.getLastFivePuzzles(username);
        assertTrue(puzzles.size() <= 5);
    }
}
