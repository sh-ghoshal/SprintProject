package com.sprint.groceryshopping.exception;

public class DuplicateRecordFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DuplicateRecordFoundException(String message) {
		super(message);
	}
}
