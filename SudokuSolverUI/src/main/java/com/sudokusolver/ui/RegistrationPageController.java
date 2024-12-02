package com.sudokusolver.ui;

import com.google.gson.Gson;
import com.sudokusolver.client.Request;
import com.sudokusolver.client.SudokuClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationPageController {
    @FXML
    private TextField idField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordAuthField;

    private SudokuClient client;
    private Gson gson;

    public RegistrationPageController() {
        gson = new Gson();
        connectToServer();
    }

    private void connectToServer() {
        try {
            client = new SudokuClient("localhost", 12345);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Connection Error", "Failed to connect to server.");
        }
    }

    @FXML
    private void handleRegistration() {
        try {
            String id = idField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String passwordAuth = passwordAuthField.getText();

            if (id.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Please enter an ID");
                return;
            }

            if (password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Please enter a password");
                return;
            }

            if (!password.equals(passwordAuth)) {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "The password you entered during authentication is incorrect, please try again");
                return;
            }

            Map<String, Object> body = new HashMap<>();
            body.put("id", id);
            body.put("username", username);
            body.put("password", password);
            Request request = new Request("registerUser", body);
            client.getWriter().println(gson.toJson(request));

            String response = client.getReader().readLine();
            if (response.equals("false")) {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "This ID is already in use");
            } else {
                client.setUsername(username);
                System.out.println(client.getUsername() + " Start Register process and Go to HomePage");
                navigateToHomePage(client.getUsername());
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Connection Error", "An error occurred during registration.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        navigateToPage("/LoginPage.fxml");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateToHomePage(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomePage.fxml"));
            Parent root = loader.load();

            HomePageController homePageController = loader.getController();
            homePageController.setClient(client); // Pass the client to the HomePageController

            Stage stage = (Stage) idField.getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load Home Page.");
        }
    }

    private void navigateToPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) idField.getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load " + fxmlFile);
        }
    }
}
