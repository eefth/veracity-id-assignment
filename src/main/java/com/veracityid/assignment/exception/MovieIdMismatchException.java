package com.veracityid.assignment.exception;

public class MovieIdMismatchException extends RuntimeException {
	
	public MovieIdMismatchException(String message) {
        super(message);
    }
	
    public MovieIdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
