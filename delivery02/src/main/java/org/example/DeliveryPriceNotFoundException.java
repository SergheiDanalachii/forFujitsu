package org.example;

// Exception class for indicating that the delivery price was not found.
public class DeliveryPriceNotFoundException extends RuntimeException{

    /**
     * Constructs a new DeliveryPriceNotFoundException with the specified detail message.
     * @param message The detail message.
     */
    public DeliveryPriceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new DeliveryPriceNotFoundException with the specified detail message and cause.
     * @param message The detail message.
     * @param cause The cause of the exception.
     */
    public DeliveryPriceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
