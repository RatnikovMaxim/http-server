package org.example.app.handler;

import org.example.server.Handler;
import org.example.server.Request;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class GetUsersHandler implements Handler {

    @Override
    public void handle(final Request request, OutputStream responseStream) throws Exception {
        final String response = "HTTP/1.1 200 OK\r\n" +
                "Connection: close\r\n" +
                "Content-Length: 2\r\n" +
                "\r\n" +
                "OK";

        responseStream.write(response.getBytes(StandardCharsets.UTF_8));
    }
}

