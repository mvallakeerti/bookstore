package com.jpacrud.jpacrud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jpacrud.jpacrud.constants.BookConstants;
import com.jpacrud.jpacrud.controller.BookController;
import com.jpacrud.jpacrud.entity.Book;
import com.jpacrud.jpacrud.entity.service.BookService;
import com.jpacrud.jpacrud.exception.BookNotFoundException;
import com.jpacrud.jpacrud.exception.BuyBookException;
import com.jpacrud.jpacrud.exception.DuplicateBookException;
import com.jpacrud.jpacrud.request.BuyBookRequest;
import com.jpacrud.jpacrud.request.SearchBookRequest;
import com.jpacrud.jpacrud.request.UpdateBookRequest;
import com.jpacrud.jpacrud.response.BookResponse;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class BookControllerTest {
	BookService bookService = mock(BookService.class);
	BookController bookController = new BookController(bookService);

	@Test
	void searchBookTest() {
		SearchBookRequest searchBookRequest = new SearchBookRequest();
		searchBookRequest.setAuthor("MALLIK");
		searchBookRequest.setTitle("PYTHON");
		searchBookRequest.setPublisher("MALLIK PUBLICATIONS");
		searchBookRequest.setPrice(1900);
        List<Book> mockStateList = new ArrayList<>();
        Book book = new Book();
        book.setId(22L);
        book.setTitle("PYTHON");
        book.setAuthor("MALLIK");
        book.setPrice(1900);
        book.setQuantity(1000);
        book.setPublisher("MALLIK PUBLICATIONS");
        book.setIsAvail("YES");
        mockStateList.add(book);
        when(bookService.searchBook(searchBookRequest)).thenReturn(mockStateList);
		ResponseEntity<BookResponse> response = bookController.searchBook(searchBookRequest);
        assertEquals(BookConstants.SUCCESS, response.getBody().getStatus());
        assertEquals(HttpStatusCode.valueOf(200) + "", response.getBody().getResponseCode());
        assertEquals(mockStateList, response.getBody().getBooks());
	}
	@Test
	void addBookTest() throws DuplicateBookException {
		List<Book> mockBookList = new ArrayList<>();
        Book book = new Book();
        book.setId(22L);
        book.setTitle("PYTHON");
        book.setAuthor("MALLIK");
        book.setPrice(1900);
        book.setQuantity(1000);
        book.setPublisher("MALLIK PUBLICATIONS");
        book.setIsAvail("YES");
        mockBookList.add(book);
        when(bookService.addBook(book)).thenReturn(book);
		ResponseEntity<BookResponse> response = bookController.addBook(book);
        assertEquals(BookConstants.SUCCESS, response.getBody().getStatus());
        assertEquals(HttpStatusCode.valueOf(200) + "", response.getBody().getResponseCode());
        assertEquals(mockBookList, response.getBody().getBooks());
	}
	@Test
	void updateBookTest() throws BookNotFoundException {
		UpdateBookRequest updateBookRequest = new UpdateBookRequest();
		updateBookRequest.setId(22L);
		updateBookRequest.setAuthor("MALLIKM");
		updateBookRequest.setTitle("PYTHONM");
		updateBookRequest.setPublisher("MALLIK PUBLICATIONSM");
		updateBookRequest.setPrice(1800);
		updateBookRequest.setQuantity(100);
		updateBookRequest.setIsAvail("YES");
        Book book = new Book();
        book.setId(22L);
        book.setTitle("PYTHONM");
        book.setAuthor("MALLIKM");
        book.setPrice(1800);
        book.setQuantity(100);
        book.setPublisher("MALLIK PUBLICATIONSM");
        book.setIsAvail("YES");
        when(bookService.updateBook(updateBookRequest)).thenReturn(book);
		ResponseEntity<BookResponse> response = bookController.updateBook(updateBookRequest);
        assertEquals(BookConstants.SUCCESS, response.getBody().getStatus());
        assertEquals(HttpStatusCode.valueOf(200) + "", response.getBody().getResponseCode());
        assertEquals(book, response.getBody().getBooks().get(0));
	}
	@Test
	void deleteBookTest() throws DuplicateBookException, NumberFormatException, BookNotFoundException {
        when(bookService.deleteBook(Long.parseLong("22"))).thenReturn("DELETED");
		ResponseEntity<BookResponse> response = bookController.deleteBook("22");
        assertEquals("SUCCESS - DELETED book.", response.getBody().getStatus());
        assertEquals(HttpStatusCode.valueOf(200) + "", response.getBody().getResponseCode());
	}
	//@Test
	void buyBookTest() throws BookNotFoundException, BuyBookException {
		BuyBookRequest buyBookRequest = new BuyBookRequest();
		buyBookRequest.setId(22L);
		buyBookRequest.setQuantity(50);
        Book book = new Book();
        book.setId(22L);
        book.setTitle("PYTHONM");
        book.setAuthor("MALLIKM");
        book.setPrice(1800);
        book.setQuantity(50);
        book.setPublisher("MALLIK PUBLICATIONSM");
        book.setIsAvail("YES");
        when(bookService.buyBook(buyBookRequest)).thenReturn(book);
		ResponseEntity<BookResponse> response = bookController.buyBook(buyBookRequest);
        assertEquals("SUCCESS - Purchased.", response.getBody().getStatus());
        assertEquals(HttpStatusCode.valueOf(200) + "", response.getBody().getResponseCode());
        assertEquals(book, response.getBody().getBooks().get(0));
	}
}
