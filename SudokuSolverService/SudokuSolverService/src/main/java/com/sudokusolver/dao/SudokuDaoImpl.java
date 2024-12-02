package main.java.com.sudokusolver.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.sudokusolver.dm.SudokuPuzzle;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SudokuDaoImpl implements IDao<String, SudokuPuzzle> {
    private static final String FILE_PATH = "puzzles.json";
    private static final int MAX_PUZZLES = 5;
    private Gson gson = new Gson();

    @Override
    public void delete(SudokuPuzzle entity) {
        // Implement delete logic if needed
    }

    @Override
    public SudokuPuzzle find(String username) throws IllegalArgumentException {
        HashMap<String, List<SudokuPuzzle>> puzzles = loadPuzzles();
        List<SudokuPuzzle> userPuzzles = puzzles.get(username);
        return userPuzzles != null && !userPuzzles.isEmpty() ? userPuzzles.get(userPuzzles.size() - 1) : null;
    }

    @Override
    public boolean save(SudokuPuzzle puzzle) throws IllegalArgumentException {
        HashMap<String, List<SudokuPuzzle>> puzzles = loadPuzzles();
        List<SudokuPuzzle> userPuzzles = puzzles.computeIfAbsent(puzzle.getUsername(), k -> new ArrayList<>());
        if (userPuzzles.size() >= MAX_PUZZLES) {
            userPuzzles.remove(0); // Remove the oldest puzzle to maintain the size
        }
        userPuzzles.add(puzzle);
        return savePuzzles(puzzles);
    }

    public List<SudokuPuzzle> findLastFive(String username) {
        HashMap<String, List<SudokuPuzzle>> puzzles = loadPuzzles();
        List<SudokuPuzzle> lastFivePuzzles = new ArrayList<>();
        List<SudokuPuzzle> userPuzzles = puzzles.get(username);
        if (userPuzzles != null && !userPuzzles.isEmpty()) {
            lastFivePuzzles = userPuzzles.subList(Math.max(userPuzzles.size() - MAX_PUZZLES, 0), userPuzzles.size());
        }
        return lastFivePuzzles;
    }

    private HashMap<String, List<SudokuPuzzle>> loadPuzzles() {
        HashMap<String, List<SudokuPuzzle>> puzzles = new HashMap<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            savePuzzles(puzzles); // Create the file if it doesn't exist
        } else {
            try (Reader reader = new FileReader(file)) {
                Type type = new TypeToken<HashMap<String, List<SudokuPuzzle>>>() {}.getType();
                puzzles = gson.fromJson(reader, type);
                if (puzzles == null) {
                    puzzles = new HashMap<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return puzzles;
    }

    private boolean savePuzzles(HashMap<String, List<SudokuPuzzle>> puzzles) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(puzzles, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
