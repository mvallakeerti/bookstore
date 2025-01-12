package com.jpacrud.jpacrud.specification;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.jpacrud.jpacrud.entity.Book;
import com.jpacrud.jpacrud.request.SearchBookRequest;

import jakarta.persistence.criteria.Predicate;
@Component
public class BookSpecification {
	private BookSpecification() {
		super();
	}
	public static Specification<Book> filterVendor(SearchBookRequest searchBookRequest) {
	    return (root, query, criteriaBuilder) -> {
	        List<Predicate> predicates = new ArrayList<>();

	        if (searchBookRequest.getId() > 0) {
	            predicates.add(criteriaBuilder.equal(root.get("id"), searchBookRequest.getId()));
	        }
	        if (!StringUtils.isBlank(searchBookRequest.getTitle())) {
	            predicates.add(criteriaBuilder.equal(root.get("title"), searchBookRequest.getTitle()));
	        }
	        if (!StringUtils.isBlank(searchBookRequest.getAuthor())) {
	            predicates.add(criteriaBuilder.equal(root.get("author"), searchBookRequest.getAuthor()));
	        }
	        if (!StringUtils.isBlank(searchBookRequest.getPublisher())) {
	            predicates.add(criteriaBuilder.equal(root.get("publisher"), searchBookRequest.getPublisher()));
	        }
	        if (searchBookRequest.getPrice() > 0.00) {
	            predicates.add(criteriaBuilder.equal(root.get("price"), searchBookRequest.getPrice()));
	        }

	        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	    };
	}
}
