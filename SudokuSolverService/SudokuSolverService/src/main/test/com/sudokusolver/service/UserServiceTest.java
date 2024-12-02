package main.test.com.sudokusolver.service;

import main.java.com.sudokusolver.dm.User;
import main.java.com.sudokusolver.dao.UserDaoImpl;
import main.java.com.sudokusolver.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService userService;
    private static final String FILE_PATH = "users.json";

    @Before
    public void setUp() throws IOException {
        clearUserData();
        userService = new UserService(new UserDaoImpl());
    }

    private void clearUserData() throws IOException {
        // Clear the contents of users.json file
        new FileWriter(FILE_PATH, false).close();
    }

    @Test
    public void testRegisterUser() {
        User user = new User(1L, "testUser", "password");
        boolean result = userService.registerUser(user);
        assertTrue("User should be registered successfully", result);

        // Trying to register the same user again should fail
        result = userService.registerUser(user);
        assertFalse("User registration should fail if user already exists", result);
    }

    @Test
    public void testLoginUser() {
        // Register a user
        User user = new User(1L, "testUser", "password");
        userService.registerUser(user);

        // Test valid login
        User loginUser = userService.loginUser(1L, "password");
        assertNotNull("Login should succeed with correct credentials", loginUser);
        assertEquals("testUser", loginUser.getName());

        // Test invalid login
        loginUser = userService.loginUser(1L, "wrongPassword");
        assertNull("Login should fail with incorrect password", loginUser);

        // Test login with non-existing user
        loginUser = userService.loginUser(2L, "password");
        assertNull("Login should fail with non-existing user", loginUser);
    }
}
