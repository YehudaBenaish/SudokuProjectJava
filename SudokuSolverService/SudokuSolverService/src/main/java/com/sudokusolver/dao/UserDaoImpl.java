package main.java.com.sudokusolver.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.sudokusolver.dm.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

public class UserDaoImpl implements IDao<Long, User> {
    public static final String FILE_PATH = "users.json";
    private Gson gson = new Gson();

    @Override
    public void delete(User entity) {
        // Implement delete logic if needed
    }

    @Override
    public User find(Long id) throws IllegalArgumentException {
        HashMap<Long, User> users = loadUsers();
        return users.get(id);
    }

    @Override
    public boolean save(User user) throws IllegalArgumentException {
        HashMap<Long, User> users = loadUsers();
        if (users.containsKey(user.getId())) {
            return false; // User already exists
        }
        users.put(user.getId(), user);
        return saveUsers(users);
    }

    private HashMap<Long, User> loadUsers() {
        HashMap<Long, User> users = new HashMap<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            saveUsers(users); // Create the file if it doesn't exist
        } else {
            try (Reader reader = new FileReader(file)) {
                Type type = new TypeToken<HashMap<Long, User>>() {}.getType();
                users = gson.fromJson(reader, type);
                if (users == null) {
                    users = new HashMap<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    private boolean saveUsers(HashMap<Long, User> users) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
