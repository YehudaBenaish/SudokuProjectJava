package main.java.com.sudokusolver.dm;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;
    private String name;
    private String password; // Store hashed passwords in a real application

    public User() {}

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    // Getters and Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
