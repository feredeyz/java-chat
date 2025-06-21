package org;

import org.user.User;

public class Main {
    public static void main(String[] args) {
        User.SignUp("asd", "asd");
        boolean result = User.LogIn("ads", "asd");
        System.out.println(result);
    }
}
