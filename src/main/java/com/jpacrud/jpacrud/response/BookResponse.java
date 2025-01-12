package com.jpacrud.jpacrud.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jpacrud.jpacrud.entity.Book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse {
private String responseCode;
private String status;
private String errorMessage;
private List<String> message;
private List<Book> books;
}
