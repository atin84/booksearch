package com.atin.searchweb.book.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class BookSearchValueTest {

	private BookSearchValue testValue1 = BookSearchValue.builder().keyword("a").searchTime(LocalDateTime.now()).build();
	private BookSearchValue testValue2 = BookSearchValue.builder().keyword("a").searchTime(LocalDateTime.now().minusDays(1)).build();
	private BookSearchValue testValue3 = BookSearchValue.builder().keyword("b").searchTime(LocalDateTime.now()).build();

	@Test
	public void compareTo() {

	}

	@Test
	public void equals___default___success() {
		assertTrue(testValue1.equals(testValue2));
		assertFalse(testValue2.equals(testValue3));
	}

	@Test
	public void hashCode___default___success() {
		assertEquals(testValue1.hashCode(), testValue2.hashCode());
		assertNotEquals(testValue2.hashCode(), testValue3.hashCode());
	}
}