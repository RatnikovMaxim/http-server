package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9999)
        ) {
            while (true) {
                try (
                     final Socket socket = serverSocket.accept();
                     final OutputStream out = socket.getOutputStream();
                     final InputStream in = socket.getInputStream();
                ) {
                   System.out.println(socket.getInetAddress());
                   out.write("Enter command\n".getBytes(StandardCharsets.UTF_8));

                    final byte[] buffer = new byte[4096];
                    int offset = 0;
                    int length = buffer.length;
                    while (true) {
                       final int read = in.read(buffer, offset, length);
                       offset += read;
                       length = buffer.length - offset;

                       final byte lastByte = buffer[offset - 1];
                       if (lastByte == '\n') {
                           break;
                       }
                   }
                    final String message = new String(buffer, 0, buffer.length - length, StandardCharsets.UTF_8).trim();
                    System.out.println("message = " + message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
