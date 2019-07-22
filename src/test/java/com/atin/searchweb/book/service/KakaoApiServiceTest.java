package com.atin.searchweb.book.service;

import com.atin.searchweb.book.dto.BookDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaoApiServiceTest {

	@Autowired
	private KakaoApiService kakaoApiService;

	@Test
	public void getBooks() throws Exception {
		// given
		String query = "미움받을 용기";

		// when
		Response<BookDto> book = kakaoApiService.getBooks(query, 1, 10).execute();

		// then
		assertTrue(book.isSuccessful());
		System.out.println(book.body());
	}
}