package com.veracityid.assignment.exception;

public class MovieNotFoundException extends RuntimeException {
	 
	public MovieNotFoundException(String message) {
        super(message);
    }
	
	public MovieNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
