package com.fernandez.ui;

import com.sun.nio.sctp.InvalidStreamException;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import javax.swing.*;

public class Client extends JFrame {

	private JTextField MessageText;
	private JTextArea MessageHistory;

	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;

	private ServerSocket serverSocket;
	private Socket clientSocket;

	private static int DefaultServerPort = 27015;
	private static int DefaultMaxQueue = 100;

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
					showMessage("Terminating connection...");
				} catch (Exception exception) {
					showMessage(String.format("%s\n", exception.getStackTrace()));
					exception.printStackTrace();
				} finally {
					shutdownServer();
				}
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	/**
	 *
	 * @param message
	 *
	 * @exception StreamCorruptedException
	 */
	private void sendMessage(String message) {
		try {
			outputStream.writeObject(message);
			outputStream.flush();

			showMessage("[SERVER]: " + message);
		} catch (IOException ioException) {
			showMessage("[ERROR]: " + ioException.getLocalizedMessage());
			ioException.printStackTrace();
		}
	}

	private void showMessage(final String message) {
		SwingUtilities.invokeLater(
			new Runnable() {
				@Override
				public void run() {
					MessageHistory.append(String.format("%s\n", message));
				}
			}
		);
	}

	private void resetTextField(JTextField textField) {
		textField.setText("");
	}

	private void waitForConnection() throws IOException {
		showMessage("Waiting for client connection...");
		clientSocket = serverSocket.accept();
		showMessage("Now connected to " + clientSocket.getInetAddress());
	}

	private void setupStreams() throws IOException {
		showMessage("[SERVER]: Setting up streams...");

		outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		outputStream.flush();

		inputStream = new ObjectInputStream(clientSocket.getInputStream());

		//showMessage("[SERVER]: You are now connected.");
	}

	private void activeConversation() throws IOException {
		String message = "You are now connected.";
		sendMessage(message);
		enableMessageTextField(true);

		do {
			try {
				/**
				 * ************************************
				 */
				message = (String) inputStream.readObject();
				showMessage("[CLIENT]: " + message);
			} catch (ClassNotFoundException classNotFoundException) {
				showMessage("[ERROR]: " + classNotFoundException.getLocalizedMessage());
			} catch (InvalidStreamException invalidStreamException) {
				showMessage("[ERROR]: " + invalidStreamException.getLocalizedMessage());
				invalidStreamException.printStackTrace();
			}
		} while (!message.equalsIgnoreCase("EXIT"));
	}

	private void shutdownServer() {
		showMessage("[SERVER]: Closing connections...");
		enableMessageTextField(false);

		try {
			outputStream.close();
			outputStream.close();
			clientSocket.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	private void enableMessageTextField(boolean enable) {
		SwingUtilities.invokeLater(
			new Runnable() {
				public void run() {
					MessageText.setEditable(enable);
				}
			}
		);
	}
}
