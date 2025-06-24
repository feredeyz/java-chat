package org;

import org.chat.Chat;
import org.user.User;


public class Main {
    public static void main(String[] args) {
        User feredeyz = new User("feredeyz", "123");
        User fridrix = new User("fridrix", "123");

        Chat chat = new Chat(feredeyz, fridrix);
        chat.Show();
    }
}
