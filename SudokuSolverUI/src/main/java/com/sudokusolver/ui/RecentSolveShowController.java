package com.sudokusolver.ui;

import com.sudokusolver.dm.SudokuPuzzle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RecentSolveShowController {
    @FXML
    private GridPane sudokuGrid;

    public void setPuzzle(SudokuPuzzle puzzle) {
        int[][] board = puzzle.getBoard();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField textField = (TextField) sudokuGrid.getChildren().get(row * 9 + col);
                textField.setText(String.valueOf(board[row][col]));
            }
        }
    }

    @FXML
    private void handleHome() {
        navigateTo("/HomePage.fxml");
    }

    private void navigateTo(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Stage stage = (Stage) sudokuGrid.getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
