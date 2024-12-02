package main.java.algorithm;

import java.util.HashSet;
import java.util.Set;

public class ConstraintPropagationSolver implements ISudokuSolver {

    @Override
    public boolean solveSudoku(int[][] board) {
        boolean progressMade;
        do {
            progressMade = false;
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (board[row][col] == 0) {
                        Set<Integer> possibleValues = getPossibleValues(board, row, col);
                        if (possibleValues.size() == 1) {
                            board[row][col] = possibleValues.iterator().next();
                            progressMade = true;
                        }
                    }
                }
            }
        } while (progressMade && !isSolved(board));
        return isSolved(board);
    }

    private Set<Integer> getPossibleValues(int[][] board, int row, int col) {
        Set<Integer> possibleValues = new HashSet<>();
        for (int i = 1; i <= 9; i++) possibleValues.add(i);
        for (int i = 0; i < 9; i++) {
            possibleValues.remove(board[row][i]);
            possibleValues.remove(board[i][col]);
            possibleValues.remove(board[row - row % 3 + i / 3][col - col % 3 + i % 3]);
        }
        return possibleValues;
    }

    private boolean isSolved(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
