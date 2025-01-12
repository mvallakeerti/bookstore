package com.jpacrud.jpacrud.exception;

import com.jpacrud.jpacrud.entity.Book;
import com.jpacrud.jpacrud.request.BuyBookRequest;

import lombok.Getter;

@Getter
public class BuyBookException extends Exception {
	private static final long serialVersionUID = 1L;
	private final BuyBookRequest buyBookRequest;
	private final Book book;

	public BuyBookException(BuyBookRequest buyBookRequest, Book book) {
		this.buyBookRequest = buyBookRequest;
		this.book = book;
	}
}
