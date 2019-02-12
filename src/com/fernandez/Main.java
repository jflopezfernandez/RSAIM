package com.fernandez;

import com.fernandez.ui.*;

import javax.swing.*;

public class Main {
    public static void main(final String[] args) {
        Server server = new Server();
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        server.run();
    }
}
