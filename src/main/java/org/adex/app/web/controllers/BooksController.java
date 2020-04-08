package org.adex.app.web.controllers;

import java.util.List;

import org.adex.app.models.Book;
import org.adex.app.querydsl.utilities.BookSpecificatinBuilder;
import org.adex.app.querydsl.utilities.BookSpecification;
import org.adex.app.querydsl.utilities.SearchCriteria;
import org.adex.app.repositories.BookRepositorySpringDataSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
class BooksController {

	private final BookRepositorySpringDataSpecifications dao;
	private BookSpecification specification;
	private final BookSpecificatinBuilder builder;

	@GetMapping
	public ResponseEntity<?> fetchAll() {
		return new ResponseEntity<List<Book>>(this.dao.findAll(), HttpStatus.OK);
	}

	@GetMapping("/byTitle/{title}")
	public ResponseEntity<?> fetchByTitleLike(@PathVariable String title) {
		this.specification.setCriteria(SearchCriteria.builder().key("title").operation(":").value(title).build());
		return new ResponseEntity<List<Book>>(this.dao.findAll(this.specification), HttpStatus.OK);
	}

	@GetMapping("/byYear/{year}")
	public ResponseEntity<?> fetchByTitleLike(@PathVariable int year) {
		return new ResponseEntity<List<Book>>(this.dao.findAll(builder.with("yearPublished", ">=", year).build()),
				HttpStatus.OK);
	}

	@GetMapping("byTitle/{title}/byYear/{year}")
	public ResponseEntity<?> fetchByTitleLikeOrYearGreatherOrEqual(@PathVariable String title, @PathVariable int year) {
		return new ResponseEntity<List<Book>>(
				this.dao.findAll(builder.with("title", ":", title).with("yearPublished", ">=", year, true).build()),
				HttpStatus.OK);
	}
}