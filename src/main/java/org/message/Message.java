package org.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Message {
    @JsonProperty("content")
    private final String content;
    @JsonProperty("from_username")
    private final String from_username;
    @JsonProperty("to_username")
    private final String to_username;

    public String getContent() {
        return content;
    }

    public String getFrom() {
        return from_username;
    }

    public String getTo() {
        return to_username;
    }

    @JsonCreator
    public Message(
            @JsonProperty("content")
            String content,
            @JsonProperty("from_username")
            String from_username,
            @JsonProperty("to_username")
            String to_username
        )
    {
        this.content = Objects.requireNonNull(content, "Содержание не может быть null");
        this.from_username = Objects.requireNonNull(from_username, "Отправитель не может быть null");
        this.to_username = Objects.requireNonNull(to_username, "Получатель не может быть null");

    }

    public Message() {
        this.content = "";
        this.from_username = "";
        this.to_username = "";
    }

    private static String[] sortUsers(
            String from, String to
    ) {
        if (from.length() > to.length()) {
            return new String[] {from, to};
        } else {
            return new String[] {to, from};
        }
    }

    public boolean writeMessage() {
        ObjectMapper mapper = new ObjectMapper();
        String[] sortedUsers = sortUsers(this.from_username, this.to_username);
        String chatFile = String.format("%s-%s.json", sortedUsers[0], sortedUsers[1]);
        ArrayList<Message> messages = getMessages(this.from_username, this.to_username);
        assert messages != null;
        messages.add(this);
        try {
            mapper.writeValue(new File(chatFile), messages);
            return true;
        } catch (IOException e) {
            System.err.printf("Ошибка: %s\n", e.getMessage());
            return false;
        }
    }

    public static ArrayList<Message> getMessages(String from, String to) {
        ObjectMapper mapper = new ObjectMapper();
        String[] sortedUsers = sortUsers(from, to);
        String chatFile = String.format("messages/%s-%s.json", sortedUsers[0], sortedUsers[1]);
        try {
            return mapper.readValue(new File(chatFile), new TypeReference<ArrayList<Message>>() {});
        } catch (IOException e) {
            System.err.printf("Ошибка: %s\n", e.getMessage());
            return null;
        }
    }
}

