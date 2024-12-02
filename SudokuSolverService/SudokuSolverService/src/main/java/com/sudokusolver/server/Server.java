package main.java.com.sudokusolver.server;

import main.java.com.sudokusolver.controller.SudokuController;
import main.java.com.sudokusolver.dao.SudokuDaoImpl;
import main.java.com.sudokusolver.dao.UserDaoImpl;
import main.java.com.sudokusolver.service.SudokuService;
import main.java.com.sudokusolver.service.UserService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void start() {
        try {
            ServerSocket server = new ServerSocket(12345);
            SudokuController sudokuController = new SudokuController(new SudokuService(new SudokuDaoImpl(), new UserDaoImpl()));
            UserService userService = new UserService(new UserDaoImpl());
            while (true) {
                Socket someClient = server.accept();
                main.java.com.sudokusolver.server.HandleRequest request = new main.java.com.sudokusolver.server.HandleRequest(someClient, sudokuController, userService  );
                new Thread(() -> request.process()).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Tired of waiting for connection");
        }
    }
}
