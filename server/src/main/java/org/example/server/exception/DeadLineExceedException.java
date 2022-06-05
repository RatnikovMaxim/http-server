package org.example.server.exception;

public class DeadLineExceedException extends RuntimeException {
    public DeadLineExceedException() {
    }

    public DeadLineExceedException(String message) {
        super(message);
    }

    public DeadLineExceedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeadLineExceedException(Throwable cause) {
        super(cause);
    }

    public DeadLineExceedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
