package org.chat;

import org.message.Message;
import org.message.MessageHolder;
import org.user.User;

import java.util.ArrayList;

public class Chat {
    public User from;
    public User to;
    public ArrayList<Message> messages;

    public Chat(
            User from,
            User to
    ) {
        this.from = from;
        this.to = to;
        this.messages = Message.getMessages(from.getUsername(), to.getUsername());
    }


    public void Show() {
        final Object lock = new Object();
        Thread messageScanner = getThread(lock);
        messageScanner.start();

        while (true) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                for (Message msg : messages) {
                    System.out.printf("%s, %s, %s\n", msg.getContent(), msg.getFrom(), msg.getTo());
                }
            }
        }
    }

    private Thread getThread(Object lock) {
        final MessageHolder holder = new MessageHolder(this.messages);
        return new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    synchronized (lock) {
                        holder.messages = Message.getMessages(this.from.getUsername(), this.to.getUsername());
                        lock.notify();
                    }
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
