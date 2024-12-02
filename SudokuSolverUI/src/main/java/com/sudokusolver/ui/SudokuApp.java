package com.sudokusolver.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SudokuApp extends Application {
    private static final double WINDOW_WIDTH = 600;
    private static final double WINDOW_HEIGHT = 500;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/LoginPage.fxml"));
        primaryStage.setTitle("Sudoku Solver");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT); // Set fixed size
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm()); // Apply CSS
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Disable resizing
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
