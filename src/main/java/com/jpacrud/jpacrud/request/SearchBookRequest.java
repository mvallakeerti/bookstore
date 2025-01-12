package com.jpacrud.jpacrud.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class SearchBookRequest {

	private long id;
	
	private String title;
	
	private String author;
	
	private String publisher;
	
	private double price;
}
