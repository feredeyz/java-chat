package org.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class User {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")

    private String password;

    @JsonCreator
    public User(@JsonProperty("username") String username,
                @JsonProperty("password") String password
                ) {
        this.username = Objects.requireNonNull(username, "Имя пользователя не может быть null");
        this.password = Objects.requireNonNull(password, "Пароль не может быть null");
    }

    public User() {
        this.username = "";
        this.password = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private static ArrayList<User> getUsers() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("users.json"), new TypeReference<ArrayList<User>>() {});
        } catch (IOException e) {
            System.err.printf("Ошибка ввода/вывода: %s\n", e.getMessage());
            return null;
        }
    }

    public static boolean LogIn(String username, String password) {
        ArrayList<User> users = getUsers();
        assert users != null;
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static boolean writeUser(User user) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<User> users = getUsers();
        assert users != null;
        users.add(user);
        try {
            mapper.writeValue(new File("users.json"), users);
            return true;
        } catch (IOException e) {
            System.err.printf("Ошибка ввода/вывода: %s\n", e.getMessage());
            return false;
        }
    }

    public static boolean SignUp(String username, String password) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<User> users = getUsers();
        assert users != null;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return writeUser(user);
    }
}
