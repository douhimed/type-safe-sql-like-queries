package org.adex.app.repositories;

import org.adex.app.web.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

@Repository
public interface BookRepositorySpringDataJPA
		extends JpaRepository<Book, Integer>, QuerydslPredicateExecutor<Book>, QuerydslBinderCustomizer<QBook> {
	@Override
	default public void customize(QuerydslBindings bindings, QBook root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.email);
	}
}