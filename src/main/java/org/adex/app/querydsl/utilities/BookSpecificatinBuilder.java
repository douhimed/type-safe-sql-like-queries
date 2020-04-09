package org.adex.app.querydsl.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.adex.app.web.models.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

@Component
public class BookSpecificatinBuilder {

	private final List<SearchCriteria> params = new ArrayList<SearchCriteria>();

	public BookSpecificatinBuilder with(String key, String operation, Object value, boolean isOr) {
		this.params.add(SearchCriteria.builder().key(key).operation(operation).value(value).isOr(isOr).build());
		return this;
	}

	public BookSpecificatinBuilder with(String key, String operation, Object value) {
		this.params.add(SearchCriteria.builder().key(key).operation(operation).value(value).build());
		return this;
	}

	public Specification<Book> buildSpecification() {
		if (this.params.isEmpty())
			return null;

		List<Specification<Book>> specs = this.params.stream().map(BookSpecification::new).collect(Collectors.toList());
		Specification<Book> res = specs.get(0);

		for (int i = 1; i < params.size(); i++) {
			res = params.get(i).isOr() ? Specification.where(res).or(specs.get(i))
					: Specification.where(res).and(specs.get(i));
		}
		return res;
	}

	public BooleanExpression buildBooleanExpressions() {
		if (this.params.isEmpty())
			return null;

		List<BooleanExpression> predicates = params.stream().map(BookPredicate::new).map(BookPredicate::getPredicate)
				.filter(Objects::nonNull).collect(Collectors.toList());

		BooleanExpression res = Expressions.asBoolean(true).isTrue();
		predicates.forEach(res::and);
		return res;
	}

}
