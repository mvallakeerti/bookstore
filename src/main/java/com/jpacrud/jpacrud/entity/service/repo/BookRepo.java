package com.jpacrud.jpacrud.entity.service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.jpacrud.jpacrud.entity.Book;
@Repository
public interface BookRepo extends JpaRepository<Book, Long>,JpaSpecificationExecutor<Book>{

	List<Book> findByTitleOrAuthor(String title, String author);
}
