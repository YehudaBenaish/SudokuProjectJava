package com.sudokusolver.ui;

import com.sudokusolver.client.SudokuClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    @FXML
    private Label welcomeLabel;

    private SudokuClient client;

    public void setClient(SudokuClient client) {
        this.client = client;
        System.out.println("Entered to HomePage now Set the Client :" + " username: " + client.getUsername());
        updateWelcomeLabel();


    }
    private void updateWelcomeLabel() {
        if (client != null && client.getUsername() != null) {
            welcomeLabel.setText("Hello, " + client.getUsername() + ", good to see you!");
        }
    }

    @FXML
    private void handleSolveSudoku() {
        navigateTo("/SolvePage.fxml");
    }

    @FXML
    private void handleRecentSolve() {
        showAlert(Alert.AlertType.INFORMATION, "Navigate Error", "Failed to navigate to RecentSolvePage , This feature will be available in the future .");
    }

    @FXML
    private void handleExit() {
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.close();
    }

    private void navigateTo(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // Pass the client to the next controller
            Object controller = loader.getController();
            if (controller instanceof SolvePageController) {
                System.out.println(client.getUsername() + " Start Connect process to SolvePage");
                ((SolvePageController) controller).setClient(client);
            } else if (controller instanceof RecentSolveController) {
                System.out.println(client.getUsername() + " Start Connect process to RecentSolve");
                ((RecentSolveController) controller).setClient(client);
            }

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
