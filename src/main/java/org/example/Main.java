package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9999)
        ) {
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("accepted");
            }
        } catch (IOException e) {
        }
    }
}
