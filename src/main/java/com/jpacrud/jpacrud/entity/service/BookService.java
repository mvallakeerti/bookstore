package com.jpacrud.jpacrud.entity.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jpacrud.jpacrud.entity.Book;
import com.jpacrud.jpacrud.entity.service.repo.BookRepo;
import com.jpacrud.jpacrud.exception.BookNotFoundException;
import com.jpacrud.jpacrud.exception.BuyBookException;
import com.jpacrud.jpacrud.exception.DuplicateBookException;
import com.jpacrud.jpacrud.request.BuyBookRequest;
import com.jpacrud.jpacrud.request.SearchBookRequest;
import com.jpacrud.jpacrud.request.UpdateBookRequest;
import com.jpacrud.jpacrud.specification.BookSpecification;

@Service
public class BookService {
	@Autowired
	BookSpecification bookSpecification;
	BookRepo bookRepo;
	@Autowired
	public BookService(BookRepo bookRepo) {
		super();
		this.bookRepo = bookRepo;
	}

	public List<Book> getBooks() {
		return bookRepo.findAll();

	}

	public Book addBook(Book book) throws DuplicateBookException {
		SearchBookRequest searchBookRequest = new SearchBookRequest();
		searchBookRequest.setAuthor(book.getAuthor());
		searchBookRequest.setTitle(book.getTitle());
		searchBookRequest.setPublisher(book.getPublisher());
		searchBookRequest.setPrice(book.getPrice());
		List<Book> books = searchBook(searchBookRequest);
		if(null == books || books.isEmpty()) {
			return bookRepo.save(book);
		}
		throw new DuplicateBookException(book);
	}
	public Book updateBook(UpdateBookRequest book) throws BookNotFoundException {
		Book bookNotFound = new Book();
	    Book bookFound = bookRepo.findById(book.getId())
	                             .orElseThrow(() -> new BookNotFoundException(bookNotFound));
		
	    if (Objects.nonNull(book.getAuthor()) && !"".equalsIgnoreCase(book.getAuthor())) {
	    	bookNotFound.setAuthor(book.getAuthor());
	    }
	    if (Objects.nonNull(book.getPublisher()) && !"".equalsIgnoreCase(book.getPublisher())) {
	    	bookNotFound.setPublisher(book.getPublisher());
	    }
	    if (Objects.nonNull(book.getTitle()) && !"".equalsIgnoreCase(book.getTitle())) {
	    	bookNotFound.setTitle(book.getTitle());
	    }
		
	    if (Objects.nonNull(book.getPublishedDate())) {
	        bookFound.setPublishedDate(book.getPublishedDate());
	    }
	    if (book.getPrice() > 0.00) {
	        bookFound.setPrice(book.getPrice());
	    }
	    if (book.getQuantity() > 0) {
	        bookFound.setQuantity(book.getQuantity());
	        bookFound.setIsAvail("YES");
	    }
	    if (Objects.nonNull(book.getIsAvail()) && !"".equalsIgnoreCase(book.getIsAvail())) {
	        bookFound.setIsAvail(book.getIsAvail());
	    }

	    return bookRepo.save(bookFound);
	}

	public String deleteBook(long id) throws BookNotFoundException {
		Optional<Book> book = bookRepo.findById(id);
		if(book.isPresent()) {
				bookRepo.delete(book.get());
				return "DELETED";
		}else {
			Book bookNotFound = new Book();
			bookNotFound.setId(id);
			throw new BookNotFoundException(bookNotFound);
		}
	}
	
	public List<Book> searchBook(SearchBookRequest searchBookRequest) {
		final Specification<Book> specification = BookSpecification.filterVendor(searchBookRequest);
		return bookRepo.findAll(specification);
	}

	public Book buyBook(BuyBookRequest buyBookRequest) throws BookNotFoundException, BuyBookException {
		Optional<Book> book = bookRepo.findById(buyBookRequest.getId());
		if(book.isPresent()) {
			Book bookFound = book.get();
			if(bookFound.getQuantity() >= buyBookRequest.getQuantity()) {
			if((bookFound.getQuantity()-buyBookRequest.getQuantity()) == 0) {
				bookFound.setIsAvail("NO");}
			bookFound.setQuantity(bookFound.getQuantity()-buyBookRequest.getQuantity());
				 return bookRepo.save(bookFound);
			}else {
				throw new BuyBookException(buyBookRequest,bookFound);
			}
		}else {
			Book bookNotFound = new Book();
			bookNotFound.setId(buyBookRequest.getId());
			throw new BookNotFoundException(bookNotFound);
		}
	}
}
