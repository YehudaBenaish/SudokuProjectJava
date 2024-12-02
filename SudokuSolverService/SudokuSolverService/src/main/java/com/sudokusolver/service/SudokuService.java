package main.java.com.sudokusolver.service;

import main.java.com.sudokusolver.dm.SudokuPuzzle;
import main.java.com.sudokusolver.dao.SudokuDaoImpl;
import main.java.com.sudokusolver.dao.UserDaoImpl;
import main.java.algorithm.ISudokuSolver;
import main.java.algorithm.BacktrackingSolver; // Import from the JAR

import java.util.List;

public class SudokuService {
    private ISudokuSolver solver;
    private SudokuDaoImpl sudokuDao;
    private UserDaoImpl userDao;

    public SudokuService(SudokuDaoImpl sudokuDao, UserDaoImpl userDao) {
        this.solver = new BacktrackingSolver(); // You can change to use different solvers
        this.sudokuDao = sudokuDao;
        this.userDao = userDao;
    }

    public boolean solvePuzzle(SudokuPuzzle puzzle) {
        boolean result = solver.solveSudoku(puzzle.getBoard());
        if (result) {
            boolean saveResult = sudokuDao.save(puzzle);
            System.out.println("Puzzle saved: " + saveResult);
        }
        return result;
    }
    public List<SudokuPuzzle> getLastFivePuzzles(String username) {
        return sudokuDao.findLastFive(username);
    }

}
