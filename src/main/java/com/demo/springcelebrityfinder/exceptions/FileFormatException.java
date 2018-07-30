package com.demo.springcelebrityfinder.exceptions;

/**
 * Exceptions that is used when the input file is filled with wrong data
 */
public class FileFormatException extends RuntimeException {

    public FileFormatException() {
        super();
    }

    public FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileFormatException(String message) {
        super(message);
    }

    public FileFormatException(Throwable cause) {
        super(cause);
    }

}
