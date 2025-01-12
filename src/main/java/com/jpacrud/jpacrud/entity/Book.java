package com.jpacrud.jpacrud.entity;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="book")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Title is required.")
	@Column(name="TITLE")
	private String title;
	
	@NotNull(message = "Author is required.")
	@NotBlank(message = "Author is required.")
	@Column(name="AUTHOR")	
	private String author;
	
	@Min(message = "QUANTITY is required and minimum 1.", value = 0)
	@Column(name="QUANTITY")
	private int quantity;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="PUBLISHED_DATE")
	private Date publishedDate;
	
	//@DecimalMax("10.0")
	@DecimalMin("0.00") 
	@Column(name="PRICE")
	private double price;
	
	@NotBlank(message = "Publisher is required.")
	@Column(name="PUBLISHER")
	private String publisher;
	
	@NotBlank(message = "IsAvail is required.")
	@Column(name="IS_AVAIL")
	private String isAvail;
}
