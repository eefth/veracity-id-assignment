package com.veracityid.assignment.exception;

public class PlaceNotFoundException extends RuntimeException {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlaceNotFoundException(String message) {
        super(message);
    }
	
	public PlaceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
