package com.sudokusolver.ui;

import com.sudokusolver.client.SudokuClient;
import com.sudokusolver.dm.SudokuPuzzle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class RecentSolveController {
    private SudokuClient client;
    private String username;

    @FXML
    private GridPane sudokuGrid;

    public void setClient(SudokuClient client) {
        this.client = client;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    private void handleRecentSolve1() {
        handleRecentSolve(1);
    }

    @FXML
    private void handleRecentSolve2() {
        handleRecentSolve(2);
    }

    @FXML
    private void handleRecentSolve3() {
        handleRecentSolve(3);
    }

    @FXML
    private void handleRecentSolve4() {
        handleRecentSolve(4);
    }

    @FXML
    private void handleRecentSolve5() {
        handleRecentSolve(5);
    }

    @FXML
    private void handleHome() {
        navigateTo("/HomePage.fxml");
    }

    private void handleRecentSolve(int index) {
        try {
            List<SudokuPuzzle> lastFivePuzzles = client.getLastFivePuzzles(username);
            if (index <= lastFivePuzzles.size()) {
                SudokuPuzzle puzzle = lastFivePuzzles.get(index - 1);
                navigateToRecentSolveShow(puzzle);
            } else {
                showAlert(Alert.AlertType.ERROR, "No Solution", "You don't have this solution.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Connection Error", "An error occurred while fetching the solutions.");
        }
    }

    private void navigateToRecentSolveShow(SudokuPuzzle puzzle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecentSolveShow.fxml"));
            Parent root = loader.load();

            RecentSolveShowController recentSolveShowController = loader.getController();
            recentSolveShowController.setPuzzle(puzzle);

            Stage stage = (Stage) sudokuGrid.getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load RecentSolveShow page.");
        }
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
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load " + fxml);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
