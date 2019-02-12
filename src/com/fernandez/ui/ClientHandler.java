package com.fernandez.ui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler extends Thread {
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final Socket socket;
    private final Server server;
    String username;
    ClientHandler(final Socket clientSocket, final ObjectInputStream input, final ObjectOutputStream output,
                  final int id, final Server s) {
        socket = clientSocket;
        in = input;
        out = output;
        server = s;
        username = Integer.toString(id);
    }
    public void run() {
        String message = "You are now connected";
        sendMessage(message);
        sendMessage("Type your username");
        username = getMessage();
        server.showThatClientJoined(this);
        activeConversation();
    }

    private void activeConversation() {
        String message;
        showHelp();
        do {
            message = getMessage();
            final String[] input = message.split(" ", 2);
            final ClientHandler recipient = server.getClient(input[0]);
            if (recipient != null) {
                recipient.sendMessage(String.format("%s: %s", username, input[1]));
            } else if (input[0].equalsIgnoreCase("users")) {
                server.showClients(this);
            } else if (input[0].equalsIgnoreCase("help")) {
                showHelp();
            } else {
                sendMessage("No such recipient");
            }
        } while(!message.equalsIgnoreCase("EXIT"));
        closeConnection();
    }

    private void showHelp() {
        sendMessage("Type \"Users\" for a list of online users");
        sendMessage("Type \"Exit\" to quit");
        sendMessage("Type \"[User] [Message]\" to send a message to someone");
        sendMessage("Type \"Help\" to be shown this again");
    }

    private String getMessage(){
        String message = "";
        try {
            message = (String) in.readObject();
            showMessage(message, "In");
        } catch (SocketException socketException){
            showMessage(socketException.getLocalizedMessage(), "Error");
            socketException.printStackTrace();
            message = "exit";
            closeConnection();
        } catch (IOException | ClassNotFoundException exception) {
            showMessage(exception.getLocalizedMessage(), "Error");
            exception.printStackTrace();
        }
        return message;
    }

    void sendMessage(final String message) {
        try {
            out.writeObject(message);
            out.flush();
            showMessage(message, "Out");
        } catch (IOException ioException) {
            showMessage(ioException.getLocalizedMessage(), "Error");
            ioException.printStackTrace();
        }
    }
    private void showMessage(final String message, final String type) {
        server.showMessage(String.format("[%s]: %s: %s", username, type, message));
    }

    void closeConnection() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            showMessage(ioException.getLocalizedMessage(), "Error");
            this.interrupt();
        } finally {
            this.interrupt();
        }
        server.removeClient(this);
        this.interrupt();
    }

}
