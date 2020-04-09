package org.adex.app.repositories;

import org.adex.app.web.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepositorySpringDataSpecifications
		extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

}
