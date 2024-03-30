package org.example;

// Exception class for indicating errors related to the delivery service.
public class DeliveryServiceException extends Throwable {
    /**
     * Constructs a new DeliveryServiceException with the specified detail message.
     * @param message The detail message.
     */
    public DeliveryServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new DeliveryServiceException with the specified detail message and cause.
     * @param message The detail message.
     * @param cause The cause of the exception.
     */
    public DeliveryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}