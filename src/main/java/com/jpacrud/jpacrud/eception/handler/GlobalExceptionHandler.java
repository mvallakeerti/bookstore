package com.jpacrud.jpacrud.eception.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jpacrud.jpacrud.constants.BookConstants;
import com.jpacrud.jpacrud.exception.BookNotFoundException;
import com.jpacrud.jpacrud.exception.BuyBookException;
import com.jpacrud.jpacrud.exception.DuplicateBookException;
import com.jpacrud.jpacrud.exception.InvalidInputException;
import com.jpacrud.jpacrud.response.BookResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody BookResponse handleGeneralException(Exception ex) {
    	BookResponse errorResponse = new BookResponse();
    	errorResponse.setStatus("An unexpected error occurred.");
    	errorResponse.setResponseCode("An unexpected error occurred: " + ex.getMessage()+" "+ HttpStatus.INTERNAL_SERVER_ERROR);
    		return errorResponse;
    }
	
	@ExceptionHandler(value = InvalidInputException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody BookResponse handleException(InvalidInputException ex) {
		BookResponse bookResponse = new BookResponse();
		bookResponse.setMessage(ex.getErros());
		bookResponse.setResponseCode(HttpStatus.BAD_REQUEST.value() + BookConstants.EMPTY);
		return bookResponse;
	}
	
	@ExceptionHandler(value = BookNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody BookResponse handleUserNotFoundException(BookNotFoundException ex) {
		BookResponse bookResponse = new BookResponse();
		if(null != ex.getBook().getTitle()) {
		bookResponse.setStatus("Book with title  '"+ex.getBook().getTitle()+"' not found.");
		}
		else {
		bookResponse.setStatus("Book with id '"+ex.getBook().getId()+"' not found.");
		}
		bookResponse.setResponseCode(HttpStatus.NOT_FOUND.value() + BookConstants.EMPTY);
		return bookResponse;
	}
	
	@ExceptionHandler(value = BuyBookException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody BookResponse handleBuyBookException(BuyBookException ex) {
		BookResponse bookResponse = new BookResponse();
		bookResponse.setStatus("Book store has "+ex.getBook().getQuantity()+" books with id '"+ex.getBuyBookRequest().getId()+"'.");
		bookResponse.setResponseCode(HttpStatus.BAD_REQUEST.value() + BookConstants.EMPTY);
		return bookResponse;
	}
	
	@ExceptionHandler(value = DuplicateBookException.class)
	@ResponseStatus(HttpStatus.FOUND)
	public @ResponseBody BookResponse handleDuplicateBookException(DuplicateBookException ex) {
		BookResponse bookResponse = new BookResponse();
		bookResponse.setStatus("The book already exist.");
		bookResponse.setResponseCode(HttpStatus.FOUND.value() + BookConstants.EMPTY);
		return bookResponse;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody BookResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
	BookResponse bookResponse = new BookResponse();
	List<String> errors = new ArrayList<>();
	ex.getBindingResult().getAllErrors().forEach(error -> {
	String fieldName = ((FieldError) error).getField();
	String errorMessage = error.getDefaultMessage();
	errors.add(fieldName+" : "+ errorMessage);
	});
	bookResponse.setMessage(errors);
	bookResponse.setResponseCode(HttpStatus.BAD_REQUEST.value() + BookConstants.EMPTY);
	bookResponse.setStatus("BAD REQUEST");
	return bookResponse;
	}
}