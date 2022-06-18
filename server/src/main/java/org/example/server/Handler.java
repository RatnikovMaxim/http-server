package org.example.server;

import java.io.OutputStream;

public interface Handler {
    void handle (Request request, OutputStream responseStream) throws Exception;
}
