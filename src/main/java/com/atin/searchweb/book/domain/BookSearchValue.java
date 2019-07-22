package com.atin.searchweb.book.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang.builder.CompareToBuilder;

import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode(of = "keyword")
@ToString(of = "keyword")
// implements Comparable<BookSearchValue>
public class BookSearchValue {

	private String keyword;

	private LocalDateTime searchTime;

	/*@Override
	public int compareTo(BookSearchValue o) {
		if (this.keyword.equals(o.keyword)) {
			return 0;
		}

		return CompareToBuilder.reflectionCompare(o.getSearchTime(), this.getSearchTime());
	}*/
}
