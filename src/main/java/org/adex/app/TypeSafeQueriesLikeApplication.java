package org.adex.app;

import java.util.Random;
import java.util.stream.Stream;

import org.adex.app.repositories.BookRepositorySpringDataSpecifications;
import org.adex.app.web.models.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@SpringBootApplication
public class TypeSafeQueriesLikeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TypeSafeQueriesLikeApplication.class, args);
	}

}

@Component
@RequiredArgsConstructor
@Log4j2
class Runner {

	private final static Random r = new Random();
	private final BookRepositorySpringDataSpecifications dao;

	@EventListener(ApplicationReadyEvent.class)
	public void initData() {
		dao.deleteAll();
		Stream.of("Effetive Java", "React 101", "Clean Code")
				.map(title -> new Book(0, title, Runner.r.nextInt(2020 - 2000) + 2000)).map(this.dao::save)
				.forEach(log::info);
	}

}
