package main.java.com.sudokusolver.service;

import main.java.com.sudokusolver.dm.SudokuPuzzle;
import main.java.com.sudokusolver.dm.User;
import main.java.com.sudokusolver.dao.UserDaoImpl;

import java.util.List;

public class UserService {
    private UserDaoImpl userDao;

    public UserService(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public boolean registerUser(User user) {
        User existingUser = userDao.find(user.getId());
        if (existingUser != null) {
            System.out.println("User already exists: " + user.getId());
            return false; // User already exists
        }
        boolean saveResult = userDao.save(user);
        if (saveResult) {
            System.out.println("User registered successfully: " + user.getId());
        } else {
            System.out.println("User registration failed: " + user.getId());
        }
        return saveResult;
    }

    public User loginUser(Long id, String password) {
        User user = userDao.find(id);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

}
