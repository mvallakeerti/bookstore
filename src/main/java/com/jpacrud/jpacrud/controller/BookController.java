package com.jpacrud.jpacrud.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jpacrud.jpacrud.constants.BookConstants;
import com.jpacrud.jpacrud.entity.Book;
import com.jpacrud.jpacrud.entity.service.BookService;
import com.jpacrud.jpacrud.exception.BookNotFoundException;
import com.jpacrud.jpacrud.exception.BuyBookException;
import com.jpacrud.jpacrud.exception.DuplicateBookException;
import com.jpacrud.jpacrud.request.BuyBookRequest;
import com.jpacrud.jpacrud.request.SearchBookRequest;
import com.jpacrud.jpacrud.request.UpdateBookRequest;
import com.jpacrud.jpacrud.response.BookResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookController {
	
	BookService bookService;
	
	@Autowired
	public BookController(BookService bookService) {
		super();
		this.bookService = bookService;
	}
	
	@PostMapping("/getBooks")
	public ResponseEntity<BookResponse> getbooks() {
		BookResponse bookResponse = new BookResponse();
		List<Book> books = bookService.getBooks();
		bookResponse.setResponseCode(HttpStatusCode.valueOf(200) + "");
		bookResponse.setStatus(BookConstants.SUCCESS);
		bookResponse.setBooks(books);
		return new ResponseEntity<>(bookResponse, HttpStatusCode.valueOf(200));

	}

	@PostMapping("/addBook")
	public ResponseEntity<BookResponse> addBook(@RequestBody @Valid Book book) throws DuplicateBookException {
		BookResponse bookResponse = new BookResponse();
		Book bookFound = bookService.addBook(book);
		bookResponse.setResponseCode(HttpStatusCode.valueOf(200) + "");
		bookResponse.setStatus(BookConstants.SUCCESS);
		if (null != bookFound) {
			List<Book> books = new ArrayList<>();
			books.add(bookFound);
			bookResponse.setBooks(books);
		}
		return new ResponseEntity<>(bookResponse, HttpStatusCode.valueOf(200));
	}

	@PostMapping("/searchBook")
	public ResponseEntity<BookResponse> searchBook(@RequestBody @Valid SearchBookRequest searchBookRequest) {
		BookResponse bookResponse = new BookResponse();
		List<Book> books = bookService.searchBook(searchBookRequest);
		bookResponse.setResponseCode(HttpStatusCode.valueOf(200) + "");
		bookResponse.setStatus(BookConstants.SUCCESS);
		bookResponse.setBooks(books);
		return new ResponseEntity<>(bookResponse, HttpStatusCode.valueOf(200));
	}
	@PostMapping("/updateBook")
	public ResponseEntity<BookResponse> updateBook(@RequestBody @Valid UpdateBookRequest book) throws BookNotFoundException  {
		BookResponse bookResponse = new BookResponse();
		Book bookFound = bookService.updateBook(book);
		bookResponse.setResponseCode(HttpStatusCode.valueOf(200) + "");
		bookResponse.setStatus(BookConstants.SUCCESS);
		if (null != bookFound) {
			List<Book> books = new ArrayList<>();
			books.add(bookFound);
			bookResponse.setBooks(books);
		}
		return new ResponseEntity<>(bookResponse, HttpStatusCode.valueOf(200));
	}
	@PostMapping("/deleteBook/id/{id}")
	public ResponseEntity<BookResponse> deleteBook(@PathVariable String id) throws BookNotFoundException {
		BookResponse bookResponse = new BookResponse();
		long bookId = Long.parseLong(id);
		String result = bookService.deleteBook(bookId);
		bookResponse.setResponseCode(HttpStatusCode.valueOf(200) + "");
		bookResponse.setStatus("SUCCESS - "+result+" book.");
		return new ResponseEntity<>(bookResponse, HttpStatusCode.valueOf(200));
	}
	@PostMapping("/buyBook")
	public ResponseEntity<BookResponse> buyBook(@RequestBody BuyBookRequest buyBookRequest) throws BookNotFoundException, BuyBookException {
		BookResponse bookResponse = new BookResponse();
		Book bookFound = bookService.buyBook(buyBookRequest);
		bookResponse.setResponseCode(HttpStatusCode.valueOf(200) + "");
		bookResponse.setStatus("SUCCESS - Purchased.");
		if (null != bookFound) {
			List<Book> books = new ArrayList<>();
			books.add(bookFound);
			bookResponse.setBooks(books);
		}
		return new ResponseEntity<>(bookResponse, HttpStatusCode.valueOf(200));
	}
}
