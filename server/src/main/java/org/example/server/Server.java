package org.example.server;

import com.google.common.primitives.Bytes;
import lombok.Setter;
import org.example.server.exception.BadRequestException;
import org.example.server.exception.DeadLineExceedException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Setter
public class Server {

    public static final byte[] CRLFCRLF = {'\r', '\n', '\r', '\n'};
    private int port = 9999;
    private int soTimeout = 30 * 1000;
    private int readTimeout = 60 * 1000;
    private int bufferSize = 4096;

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)
        ) {
            while (true) {
                try {
                    final Socket socket = serverSocket.accept();
                    handleClient(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) throws IOException {
        socket.setSoTimeout(soTimeout);
        try (
                socket;
                final OutputStream out = socket.getOutputStream();
                final InputStream in = socket.getInputStream()
        ) {
            System.out.println(socket.getInetAddress());

            final String message = readMessage(in);
            System.out.println("message = " + message);

            final String response = "HTTP/1.1 200 OK\r\n" +
                    "Connection: close\r\n" +
                    "Content-Length: 2\r\n" +
                    "\r\n" +
                    "OK";

            out.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    private String readMessage(InputStream in) throws IOException {
        final byte[] buffer = new byte[bufferSize];
        int offset = 0;
        int length = buffer.length;

        final Instant deadLine = Instant.now().plusMillis(readTimeout);

        while (true) {
            if (Instant.now().isAfter(deadLine)) {
                throw new DeadLineExceedException();
            }
            final int read = in.read(buffer, offset, length);
            offset += read;
            length = buffer.length - offset;

            final int headersEndIndex = Bytes.indexOf(buffer, CRLFCRLF);
            if (headersEndIndex != -1) {
                break;
            }

            if (read == -1) {
                throw new BadRequestException("CRLFCRLF not found, no more data");
            }

            if (length == 0) {
                throw new BadRequestException("CRLFCRLF not found");
            }
        }
        final String message = new String(buffer, 0, buffer.length - length, StandardCharsets.UTF_8).trim();
        return message;
    }
}
