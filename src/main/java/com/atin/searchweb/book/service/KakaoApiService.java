package com.atin.searchweb.book.service;

import com.atin.searchweb.book.dto.BookDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KakaoApiService {

	@GET("/v3/search/book?target=title")
	Call<BookDto> getBooks(@Query("query") String query, @Query("page") Integer page, @Query("size") Integer size);

}
