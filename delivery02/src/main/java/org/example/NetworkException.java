package org.example;

// Exception class for indicating errors related to network operations.
public class NetworkException extends Exception{
    /**
     * Constructs a new NetworkException with the specified detail message.
     * @param message The detail message.
     */
    public NetworkException(String message) {
        super(message);
    }

    /**
     * Constructs a new NetworkException with the specified detail message and cause.
     * @param message The detail message.
     * @param cause The cause of the exception.
     */
    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}