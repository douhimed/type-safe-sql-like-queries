package org.adex.app.querydsl.utilities;

import org.adex.app.web.models.Book;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class BookPredicate {

	private SearchCriteria criteria;

	public BooleanExpression getPredicate() {

		PathBuilder<Book> entityPath = new PathBuilder<>(Book.class, "book");

		if (this.criteria.getType().equals(ValueTypes.STRING)) {
			StringPath path = entityPath.getString(this.criteria.getKey());
			return getStringExpression(path);
		} else if (this.criteria.getType().equals(ValueTypes.INTEGER)) {
			NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
			var value = Parser.toInt(this.criteria.getValue().toString());
			return getNumberExpression(path, value);
		} else if (this.criteria.getType().equals(ValueTypes.DOUBLE)) {
			NumberPath<Double> path = entityPath.getNumber(criteria.getKey(), Double.class);
			var value = Parser.toDouble(this.criteria.getValue().toString());
			return getNumberExpression(path, value);
		}

		return null;

	}

	private BooleanExpression getNumberExpression(NumberPath<Double> path, double value) {
		switch (this.criteria.getOperation()) {
		case "==" -> path.eq(value);
		case "!=" -> path.ne(value);
		case ">" -> path.gt(value);
		case ">=" -> path.goe(value);
		case "<" -> path.lt(value);
		case "<=" -> path.loe(value);
		default -> throw new IllegalArgumentException("Unexpected value: " + this.criteria.getOperation());
		}
		return null;
	}

	private BooleanExpression getNumberExpression(NumberPath<Integer> path, int value) {
		switch (this.criteria.getOperation()) {
		case "==" -> path.eq(value);
		case "!=" -> path.ne(value);
		case ">" -> path.gt(value);
		case ">=" -> path.goe(value);
		case "<" -> path.lt(value);
		case "<=" -> path.loe(value);
		default -> throw new IllegalArgumentException("Unexpected value: " + this.criteria.getOperation());
		}
		return null;
	}

	private BooleanExpression getStringExpression(StringPath path) {
		switch (this.criteria.getOperation()) {
		case ":" -> path.containsIgnoreCase(this.criteria.getValue().toString());
		case ":-" -> path.concat(this.criteria.getValue().toString());
		default -> throw new IllegalArgumentException("Unexpected value: " + this.criteria.getOperation());
		}
		return null;
	}

}
