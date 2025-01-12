package com.jpacrud.jpacrud.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class BuyBookRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "Book id is required.")
	private long id;
	@NotBlank(message = "Quantity is required.")
	private int quantity;
}
