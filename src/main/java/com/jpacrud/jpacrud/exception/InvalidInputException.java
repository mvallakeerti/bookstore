package com.jpacrud.jpacrud.exception;

import java.util.List;

import lombok.Getter;
@Getter
public class InvalidInputException extends Exception{
	private static final long serialVersionUID = 1L;
	final String message;
	private final List<String> erros;
public InvalidInputException(String message, List<String> errors) {
	this.message = message;
	this.erros = errors;
}
}
