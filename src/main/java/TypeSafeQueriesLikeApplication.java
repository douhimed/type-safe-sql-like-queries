

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.adex.app.models.Book;
import org.adex.app.querydsl.utilities.BookSpecification;
import org.adex.app.querydsl.utilities.SearchCriteria;
import org.adex.app.repositories.BookRepositorySpringDataSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
public class TypeSafeQueriesLikeApplication {

	private final static Random r = new Random();

	public static void main(String[] args) {
		SpringApplication.run(TypeSafeQueriesLikeApplication.class, args);
	}

}

@Component
class Runner {

	private final static Random r = new Random();

	@Autowired
	private BookRepositorySpringDataSpecifications dao;

	@EventListener(ApplicationReadyEvent.class)
	public void initData() {
		dao.deleteAll();
		Stream.of("Java Effective", "React 101", "Clean code")
				.map(title -> new Book(0, title, Runner.r.nextInt(2020 - 2000) + 2000)).map(dao::save)
				.forEach(System.out::println);
	}

}

