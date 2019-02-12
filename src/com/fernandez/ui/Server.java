package com.fernandez.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server extends JFrame {
    //UI
    private final JTextArea messageHistory;
    private final JTextField messageText;
    //Network
    private ServerSocket serverSocket;

    //User Handling
    private final LinkedList<ClientHandler> clients = new LinkedList<>();
    public Server() {
        super("RSAIM");
        messageHistory = new JTextArea();
        messageText = new JTextField();
        initComponents();
    }
    private void initComponents() {
        messageText.addActionListener((ActionEvent event) -> {
            serverCommand(event.getActionCommand());
            messageText.setText("");
        });
        add(messageText, BorderLayout.SOUTH);
        add(new JScrollPane(messageHistory), BorderLayout.CENTER);
        setSize(800, 600);
        setVisible(true);
    }

    private void serverCommand(String message) {
        if (message.equalsIgnoreCase("exit")) {
            closeServer();
        }
    }

    private void closeServer() {
        showMessage("Closing Server");
        for (ClientHandler clientHandler : clients) {
            clientHandler.closeConnection();
        }
        try {
            serverSocket.close();
            System.exit(0);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void run() {
        final int defaultServerPort = 27015;
        try {
            serverSocket = new ServerSocket(defaultServerPort);
            showMessage("Server Started");
            int numClients = 0;
            do {
                numClients = waitForNewClient(numClients);
            } while(numClients != 0);
        } catch (IOException ioException) {
            showMessage(ioException.getLocalizedMessage());
            ioException.printStackTrace();
        }
    }

    private int waitForNewClient(int numClients) {
        showMessage("Waiting for new client");
        try {
            final Socket clientSocket = serverSocket.accept();
            showMessage(String.format("Now connected to User %d: %s", ++numClients, clientSocket));

            final ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.flush();

            final ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

            showMessage(String.format("Creating a new thread for User %d", numClients));
            ClientHandler clientHandler = new ClientHandler(clientSocket, inputStream, outputStream, numClients, this);
            clientHandler.start();
            clients.add(clientHandler);
        } catch (IOException ioException) {
            showMessage(ioException.getLocalizedMessage());
            ioException.printStackTrace();
            closeServer();
        }
        return numClients;
    }
    void showThatClientJoined(ClientHandler clientHandler) {
        for(ClientHandler client : clients) {
            client.sendMessage(String.format("%s joined the server", clientHandler.username));
        }
    }
    void showMessage(String message) {
        SwingUtilities.invokeLater(()-> messageHistory.append(String.format("%s\n", message)));
    }

    void showClients(final ClientHandler requester) {
        requester.sendMessage("People Online");
        for(ClientHandler client : clients) {
            requester.sendMessage(String.format("%s", client.username));

        }
    }
    ClientHandler getClient(final String username) {
        for(ClientHandler client : clients) {
            if(client.username.equalsIgnoreCase(username)) {
                return client;
            }
        }
        return null;
    }
    void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}
