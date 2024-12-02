package com.sudokusolver.ui;

import com.sudokusolver.client.SudokuClient;
import com.sudokusolver.dm.SudokuPuzzle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SolvePageController {
    @FXML
    private GridPane sudokuGrid;
    private String Username;

    public GridPane getSudokuGrid() {
        return sudokuGrid;
    }

    private SudokuClient client;

    public void setUsername(String username) {
        Username = username;
    }

    public String getUsername() {
        return Username;
    }

    public void setClient(SudokuClient client) {
        this.client = client;
        System.out.println("Entered to SolvePage now Set the Client :" + " username: " + client.getUsername());
        Username = client.getUsername();
    }

    @FXML
    private void handleSolve() {
        int[][] board = getBoardFromUI();
        if (!isValidSudokuInput(board)) {
            showAlert(Alert.AlertType.ERROR, "Solve Error", "Please check the data you entered again.");
            System.out.println("Before reconnect , corrent username: " + client.getUsername());
            reconnectToServer();
            client.setUsername(Username);
            System.out.println("After reconnect , corrent username: " + client.getUsername());
            return;
        }

        SudokuPuzzle puzzle = new SudokuPuzzle(client.getUsername(), board);

        try {
            boolean solved = client.solveSudoku(puzzle);
            if (solved) {
                updateUIWithSolution(puzzle.getBoard());
            } else {
                showAlert(Alert.AlertType.ERROR, "Solve Error", "Failed to solve the Sudoku puzzle.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Connection Error", "An error occurred while solving the puzzle.");
            System.out.println("Before reconnect , corrent username: " + client.getUsername());
            reconnectToServer();
            client.setUsername(Username);
            System.out.println("After reconnect , corrent username: " + client.getUsername());
        }
    }

    @FXML
    private void handleCleanBoard() {
        clearSudokuBoard();
    }

    @FXML
    private void handleHome() {
        navigateTo("/HomePage.fxml");
    }

    private void reconnectToServer() {
        try {
            client = new SudokuClient("localhost", 12345);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Reconnection Error", "Failed to reconnect to the server.");
        }
    }

    private int[][] getBoardFromUI() {
        int[][] board = new int[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField textField = (TextField) sudokuGrid.getChildren().get(row * 9 + col);
                String text = textField.getText();
                if (text.isEmpty()) {
                    return null; // Return null if any cell is empty
                }
                board[row][col] = Integer.parseInt(text);
            }
        }
        return board;
    }

    private boolean isValidSudokuInput(int[][] board) {
        if (board == null) {
            return false;
        }
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = board[row][col];
                if (value < 0 || value > 9) {
                    return false;
                }
            }
        }
        return true;
    }

    private void updateUIWithSolution(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField textField = (TextField) sudokuGrid.getChildren().get(row * 9 + col);
                textField.setText(String.valueOf(board[row][col]));
            }
        }
    }

    private void clearSudokuBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField textField = (TextField) sudokuGrid.getChildren().get(row * 9 + col);
                textField.clear();
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateTo(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // Pass the client to the next controller
            Object controller = loader.getController();
            if (controller instanceof HomePageController) {
                System.out.println(client.getUsername() + " Start process and then Go to HomePage");
                ((HomePageController) controller).setClient(client);
            }

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
