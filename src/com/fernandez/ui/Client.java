package com.fernandez.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.net.http.*;
import javax.swing.*;

public class Client extends JFrame {

	private JTextField MessageText;
	private JTextArea MessageHistory;

	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;

	private ServerSocket serverSocket;
	private Socket clientSocket;

	private int DefaultServerPort = 27015;
	private int DefaultMaxQueue = 100;

	public Client() {
		super("RSAIM");
		initComponents();
	}

	private void initComponents() {
		// Configure message text box.
		MessageText = new JTextField();
		MessageText.setEditable(false);
		MessageText.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						sendMessage(event.getActionCommand());
						MessageText.setText("");
					}
				}
		);

		add(MessageText, BorderLayout.NORTH);

		// Configure message history.
		MessageHistory = new JTextArea();
		add(new JScrollPane(MessageHistory));

		// Application window configuration settings.
		setSize(800, 600);
		setVisible(true);
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(DefaultServerPort, DefaultMaxQueue);

			while (true) {
				try {
					waitForConnection();
					setupStreams();
					activeConversation();
				} catch (EOFException eofException) {
					System.err.println("Terminating connection...");
				} finally {
					shutdownServer();
				}
			}
		} catch (IOException ioException) {
			System.err.println(ioException.getLocalizedMessage());
		}
	}

	private void sendMessage(String message) {
		try {
			outputStream.writeObject("[SERVER]: " + message);
			outputStream.flush();

			showMessage("[SERVER]: " + message);
		} catch (IOException ioException) {
			MessageHistory.append("[ERROR]: " + ioException.getLocalizedMessage());
		}
	}

	private void showMessage(final String message) {
		SwingUtilities.invokeLater(
			new Runnable() {
				@Override
				public void run() {
					MessageHistory.append(message);
				}
			}
		);
	}

	private void resetTextField(JTextField textField) {
		textField.setText("");
	}

	private void waitForConnection() throws IOException {
		System.out.println("Waiting for client connection...");
		clientSocket = serverSocket.accept();
		System.out.println("Now connected to " + clientSocket.getInetAddress());
	}

	private void setupStreams() throws IOException {
		showMessage("[SERVER]: Setting up streams...");

		outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		outputStream.flush();

		inputStream = new ObjectInputStream(clientSocket.getInputStream());

		showMessage("[SERVER]: You are now connected.");
	}

	private void activeConversation() throws IOException {
		String message = "You are now connected.";
		sendMessage("[SERVER]: " + message);
		MessageText.setEditable(true);

		do {
			try {
				message = (String) inputStream.readObject();
				showMessage("[CLIENT]: " + message);
			} catch (ClassNotFoundException classNotFoundException) {
				showMessage("[ERROR]: " + classNotFoundException.getLocalizedMessage());
			}
		} while (!message.equalsIgnoreCase("EXIT"));
	}

	private void shutdownServer() {
		showMessage("[SERVER]: Closing connections...");
		MessageText.setEditable(false);

		try {
			outputStream.close();
			outputStream.close();
			clientSocket.close();
		} catch (IOException ioException) {
			System.out.println("[ERROR]: " + ioException.getLocalizedMessage());
		}
	}
}
