package com.jpacrud.jpacrud.exception;

import com.jpacrud.jpacrud.entity.Book;
import lombok.Getter;

@Getter
public class DuplicateBookException extends Exception {
	private static final long serialVersionUID = 1L;
	private final Book book;

	public DuplicateBookException(Book book) {
		this.book = book;
	}
}
