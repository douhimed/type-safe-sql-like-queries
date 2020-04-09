package org.adex.app.querydsl.utilities;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class SearchCriteria {

	private String key;
	private String operation;
	private Object value;
	@Builder.Default
	private boolean isOr = false;
	@Builder.Default
	private ValueTypes type= ValueTypes.STRING;
}
