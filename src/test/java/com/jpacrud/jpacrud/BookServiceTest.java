package com.jpacrud.jpacrud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jpacrud.jpacrud.entity.Book;
import com.jpacrud.jpacrud.entity.service.BookService;
import com.jpacrud.jpacrud.entity.service.repo.BookRepo;
import com.jpacrud.jpacrud.exception.BookNotFoundException;
import com.jpacrud.jpacrud.exception.BuyBookException;
import com.jpacrud.jpacrud.exception.DuplicateBookException;
import com.jpacrud.jpacrud.request.BuyBookRequest;
import com.jpacrud.jpacrud.request.SearchBookRequest;
import com.jpacrud.jpacrud.request.UpdateBookRequest;
import com.jpacrud.jpacrud.specification.BookSpecification;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class BookServiceTest {
	BookRepo bookRepo = mock(BookRepo.class);
	BookService bookService = new BookService(bookRepo);
    @Mock
    private BookSpecification bookSpecification;
    @SuppressWarnings("unchecked")
	private Specification<Book> specification = mock(Specification.class);
	@Test
	void searchBookTest() {
		SearchBookRequest searchBookRequest = new SearchBookRequest();
		searchBookRequest.setAuthor("JAMES GOSLING");
		searchBookRequest.setTitle("JAVA");
		searchBookRequest.setPublisher("AVA PUBLISHER");
		searchBookRequest.setPrice(300.51);
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
        when(BookSpecification.filterVendor(searchBookRequest)).thenReturn(specification);
        when(bookRepo.findAll(specification)).thenReturn(mockStateList);
        List<Book> response = bookService.searchBook(searchBookRequest);
        assertEquals(0, response.size());
	}
	@Test
	void addBookTest() throws DuplicateBookException {
        Book book = new Book();
        book.setId(22L);
        book.setTitle("PYTHON");
        book.setAuthor("MALLIK");
        book.setPrice(1900);
        book.setQuantity(1000);
        book.setPublisher("MALLIK PUBLICATIONS");
        book.setIsAvail("YES");
        when(bookRepo.save(book)).thenReturn(book);
		Book response = bookService.addBook(book);
        assertEquals(book, response);
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
        Book book =  new Book();
        book.setId(22L);
        book.setTitle("PYTHONM");
        book.setAuthor("MALLIKM");
        book.setPrice(1800);
        book.setQuantity(100);
        book.setPublisher("MALLIK PUBLICATIONSM");
        book.setIsAvail("YES");
        Optional<Book> optBook = Optional.of(book);
        when(bookRepo.findById(Long.parseLong("22"))).thenReturn(optBook);
        when(bookRepo.save(book)).thenReturn(book);
		Book response = bookService.updateBook(updateBookRequest);
        assertEquals(book, response);
	}
	@Test
	void deleteBookTest() throws DuplicateBookException, NumberFormatException, BookNotFoundException {
        Book book =  new Book();
        book.setId(22L);
        book.setTitle("PYTHONM");
        book.setAuthor("MALLIKM");
        book.setPrice(1800);
        book.setQuantity(100);
        book.setPublisher("MALLIK PUBLICATIONSM");
        book.setIsAvail("YES");
        Optional<Book> optBook = Optional.of(book);
		when(bookRepo.findById(Long.parseLong("22"))).thenReturn(optBook);
		doNothing().when(bookRepo).delete(new Book());
		String response = bookService.deleteBook(Long.parseLong("22"));
        assertEquals("DELETED", response);
	}
	@Test
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
        Optional<Book> optBook = Optional.of(book);
        when(bookRepo.save(book)).thenReturn(book);
        when(bookRepo.findById(Long.parseLong("22"))).thenReturn(optBook);
		Book response = bookService.buyBook(buyBookRequest);
        assertEquals(book, response);
	}
}
