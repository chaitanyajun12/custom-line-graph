package org.custom.graph.exception;

public class GraphException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public GraphException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
}
