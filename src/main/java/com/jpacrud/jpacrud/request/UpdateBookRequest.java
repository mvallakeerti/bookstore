package com.jpacrud.jpacrud.request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookRequest {
	@NotNull(message = "The ID cannot be null")
	@Min(value = 1, message = "ID should be greater than 0.")
	private long id;

	private String title;

	private String author;

	private String publisher;

	@DecimalMin("0.00")
	private double price;

	private int quantity;

	private String isAvail;

	private Date publishedDate;
}
