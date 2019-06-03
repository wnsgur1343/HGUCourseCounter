package edu.handong.analysis.utils;

public class NotEnoughArgumentException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotEnoughArgumentException() {
		super ("No CLI argument Exception! Please put a file path.");

	}
	
	public NotEnoughArgumentException(String message) {
		super (message);
	}

}
