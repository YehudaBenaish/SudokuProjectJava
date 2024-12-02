package main.java.com.sudokusolver.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.com.sudokusolver.controller.SudokuController;
import main.java.com.sudokusolver.dm.SudokuPuzzle;
import main.java.com.sudokusolver.dm.User;
import main.java.com.sudokusolver.service.UserService;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class HandleRequest {
    private SudokuController sudokuController;
    private UserService userService;
    private Socket someClient;
    private Gson gson = new GsonBuilder().create();

    public HandleRequest(Socket someClient, SudokuController sudokuController, UserService userService) {
        this.someClient = someClient;
        this.sudokuController = sudokuController;
        this.userService = userService;
    }

    public void process() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.someClient.getInputStream()));
            PrintWriter writer = new PrintWriter(this.someClient.getOutputStream(), true);
            String line = reader.readLine();
            Request request = gson.fromJson(line, Request.class);
            String action = request.getAction();
            Map<String, Object> body = request.getBody();

            switch (action) {
                case "solvePuzzle":
                    SudokuPuzzle puzzle = gson.fromJson(gson.toJson(body.get("puzzle")), SudokuPuzzle.class);
                    // Debug: Print received puzzle
                    printBoard("Received Puzzle:", puzzle.getBoard());

                    boolean solved = sudokuController.solvePuzzle(puzzle);

                    // Debug: Print solved puzzle
                    if (solved) {
                        printBoard("Solved Puzzle:", puzzle.getBoard());
                    }

                    writer.println(gson.toJson(solved ? puzzle : null));
                    break;
                case "getLastFivePuzzles":
//                    Long userId = Long.parseLong((String) body.get("username"));
//                    List<SudokuPuzzle> lastFivePuzzles = .getLastFivePuzzles(userId);
//                    writer.println(gson.toJson(lastFivePuzzles));
                    break;
                case "loginUser":
                    String loginId = (String) body.get("id");
                    String loginPassword = (String) body.get("password");
                    User user = userService.loginUser(Long.parseLong(loginId), loginPassword);
                    if (user != null) {
                        writer.println(gson.toJson(user));
                    } else {
                        writer.println("null");
                    }
                    break;
                case "registerUser":
                    String registerId = (String) body.get("id");
                    String username = (String) body.get("username");
                    String registerPassword = (String) body.get("password");
                    User newUser = new User(Long.parseLong(registerId), username, registerPassword);
                    boolean registrationResult = userService.registerUser(newUser);
                    writer.println(registrationResult ? "true" : "false");
                    break;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printBoard(String title, int[][] board) {
        System.out.println(title);
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
