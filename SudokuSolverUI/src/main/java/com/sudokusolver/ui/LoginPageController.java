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

public class LoginPageController {
    @FXML
    private TextField idField;
    @FXML
    private PasswordField passwordField;

    private SudokuClient client;
    private Gson gson;

    public LoginPageController() {
        gson = new Gson();
        connectToServer();
    }

    private void connectToServer() {
        try {
            System.out.println("Connecting to server...");
            client = new SudokuClient("localhost", 12345);
            System.out.println("Connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Connection Error", "Failed to connect to server.");
        }
    }

    @FXML
    private void handleLogin() {
        try {
            if (client == null || client.getSocket().isClosed()) {
                System.out.println("Reconnecting to server...");
                connectToServer(); // Ensure the client is connected
            }

            String id = idField.getText();
            String password = passwordField.getText();

            if (id.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter your ID.");
                return;
            }

            if (password.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter your password.");
                return;
            }

            Map<String, Object> body = new HashMap<>();
            body.put("id", id);
            body.put("password", password);
            Request request = new Request("loginUser", body);
            client.getWriter().println(gson.toJson(request));

            System.out.println("Sent login request: " + gson.toJson(request));

            String response = client.getReader().readLine();
            System.out.println("Received response: " + response);
            if ("null".equals(response)) {
                showAlert(Alert.AlertType.INFORMATION, "Login Failed", "Please register, if you have registered before, please check the data again");
                connectToServer(); // Reconnect to server in case of error
            } else {
                // Extract the username from the response
                Map<String, Object> responseMap = gson.fromJson(response, HashMap.class);
                String username = (String) responseMap.get("name");
                client.setUsername(username);
                System.out.println(client.getUsername() + " Start Login process and then Go to HomePage");
                navigateToHomePage(client.getUsername());
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Connection Error", "An error occurred during login.");
            connectToServer(); // Reconnect to server in case of error
        }
    }

    @FXML
    private void handleRegistration() {
        System.out.println("New User Go tho RegistrationPage");
        navigateToPage("/RegistrationPage.fxml");
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


            System.out.println("In LoginPage now try to move my client to HomePage username: " + client.getUsername());
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
            System.out.println("Loading FXML file: " + fxmlFile);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) idField.getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Failed to load " + fxmlFile + ". Exception: " + e.getMessage());
        }
    }
}
