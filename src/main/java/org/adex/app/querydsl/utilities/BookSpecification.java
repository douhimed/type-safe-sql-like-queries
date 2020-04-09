package org.adex.app.querydsl.utilities;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.adex.app.web.models.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class BookSpecification implements Specification<Book> {

	private static final long serialVersionUID = 1L;

	private SearchCriteria criteria;

	@Override
	public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		switch (this.criteria.getOperation()) {
		case "==" -> builder.equal(root.get(this.criteria.getKey()), this.criteria.getValue().toString());
		case "!=" -> builder.notEqual(root.get(this.criteria.getKey()), this.criteria.getValue().toString());
		case ">" -> builder.greaterThan(root.get(this.criteria.getKey()), this.criteria.getValue().toString());
		case ">=" -> builder.greaterThanOrEqualTo(root.get(this.criteria.getKey()),
				this.criteria.getValue().toString());
		case "<" -> builder.lessThan(root.get(this.criteria.getKey()), this.criteria.getValue().toString());
		case "<=" -> builder.lessThanOrEqualTo(root.get(this.criteria.getKey()), this.criteria.getValue().toString());
		case ":" -> builder.like(root.get(this.criteria.getKey()), "%" + this.criteria.getValue().toString() + "%");
		default -> throw new IllegalArgumentException("Unexpected value: " + this.criteria.getOperation());
		}

		return null;
	}

}