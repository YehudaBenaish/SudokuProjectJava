package com.sudokusolver.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sudokusolver.dm.SudokuPuzzle;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SudokuClient {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Gson gson;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SudokuClient(String serverAddress, int serverPort) throws IOException {
        connect(serverAddress, serverPort);
    }

    private void connect(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
        gson = new GsonBuilder().create();
    }

    public boolean solveSudoku(SudokuPuzzle puzzle) throws IOException {
        Map<String, Object> body = new HashMap<>();
        body.put("puzzle", puzzle);
        Request request = new Request("solvePuzzle", body);
        writer.println(gson.toJson(request));

        // Debug: Print request
        System.out.println("Sent request: " + gson.toJson(request));

        String response = reader.readLine();

        // Debug: Print response
        System.out.println("Received response: " + response);

        SudokuPuzzle solvedPuzzle = gson.fromJson(response, SudokuPuzzle.class);
        if (solvedPuzzle != null) {
            puzzle.setBoard(solvedPuzzle.getBoard());
            return true;
        }
        return false;
    }

    public List<SudokuPuzzle> getLastFivePuzzles(String username) throws IOException {
        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        Request request = new Request("getLastFivePuzzles", body);
        writer.println(gson.toJson(request));
        String response = reader.readLine();
        return gson.fromJson(response, List.class);
    }

    public void close() throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public Socket getSocket() {
        return socket;
    }
}
