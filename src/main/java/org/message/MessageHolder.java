package org.message;

import java.util.ArrayList;

public class MessageHolder {
    public ArrayList<Message> messages;
    public MessageHolder(ArrayList<Message> messages) { this.messages = messages; }
    public ArrayList<Message> getMessages() {
        return this.messages;
    }
}
