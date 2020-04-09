package org.adex.app.repositories;

import org.adex.app.web.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepositorySpringDataJPA extends JpaRepository<Book, Integer>, QuerydslPredicateExecutor<Book> {

}