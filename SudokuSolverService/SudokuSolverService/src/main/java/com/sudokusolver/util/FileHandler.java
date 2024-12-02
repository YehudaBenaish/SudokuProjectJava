package main.java.com.sudokusolver.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.sudokusolver.dm.SudokuPuzzle;
import main.java.com.sudokusolver.dm.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileHandler {
    private static final String USERS_FILE = "users.json";
    private static final String PUZZLES_FILE = "puzzles.json";
    private static final int MAX_PUZZLES = 5;
    private static Gson gson = new Gson();

    public static boolean saveUser(User user) {
        HashMap<Long, User> users = loadUsers();
        users.put(user.getId(), user);
        return saveUsers(users);
    }

    public static User findUser(Long id, String password) {
        HashMap<Long, User> users = loadUsers();
        User user = users.get(id);
        return user != null && user.getPassword().equals(password) ? user : null;
    }

    public static boolean savePuzzle(String username, SudokuPuzzle puzzle) {
        HashMap<String, List<SudokuPuzzle>> puzzles = loadPuzzles();
        List<SudokuPuzzle> userPuzzles = puzzles.computeIfAbsent(username, k -> new ArrayList<>());
        if (userPuzzles.size() >= MAX_PUZZLES) {
            userPuzzles.remove(0); // Remove the oldest puzzle to maintain the size
        }
        userPuzzles.add(puzzle);
        return savePuzzles(puzzles);
    }

    public static List<SudokuPuzzle> getLastFivePuzzles(String username) {
        HashMap<String, List<SudokuPuzzle>> puzzles = loadPuzzles();
        List<SudokuPuzzle> lastFivePuzzles = new ArrayList<>();
        List<SudokuPuzzle> userPuzzles = puzzles.get(username);
        if (userPuzzles != null && !userPuzzles.isEmpty()) {
            lastFivePuzzles = userPuzzles.subList(Math.max(userPuzzles.size() - MAX_PUZZLES, 0), userPuzzles.size());
        }
        return lastFivePuzzles;
    }

    private static HashMap<Long, User> loadUsers() {
        HashMap<Long, User> users = new HashMap<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            saveUsers(users); // Create the file if it doesn't exist
        } else {
            try (Reader reader = new FileReader(file)) {
                Type type = new TypeToken<HashMap<Long, User>>() {}.getType();
                users = gson.fromJson(reader, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    private static boolean saveUsers(HashMap<Long, User> users) {
        try (Writer writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static HashMap<String, List<SudokuPuzzle>> loadPuzzles() {
        HashMap<String, List<SudokuPuzzle>> puzzles = new HashMap<>();
        File file = new File(PUZZLES_FILE);
        if (!file.exists()) {
            savePuzzles(puzzles); // Create the file if it doesn't exist
        } else {
            try (Reader reader = new FileReader(file)) {
                Type type = new TypeToken<HashMap<String, List<SudokuPuzzle>>>() {}.getType();
                puzzles = gson.fromJson(reader, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return puzzles;
    }

    private static boolean savePuzzles(HashMap<String, List<SudokuPuzzle>> puzzles) {
        try (Writer writer = new FileWriter(PUZZLES_FILE)) {
            gson.toJson(puzzles, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
