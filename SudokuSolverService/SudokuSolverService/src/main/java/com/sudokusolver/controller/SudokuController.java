package main.java.com.sudokusolver.controller;

import main.java.com.sudokusolver.dm.SudokuPuzzle;
import main.java.com.sudokusolver.service.SudokuService;

import java.util.List;

public class SudokuController {
    private SudokuService sudokuService;

    public SudokuController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }

    public boolean solvePuzzle(SudokuPuzzle puzzle) {
        return sudokuService.solvePuzzle(puzzle);
    }

    public List<SudokuPuzzle> getLastFivePuzzles(String username) {
        return sudokuService.getLastFivePuzzles(username);
    }
}
