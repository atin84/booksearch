package com.atin.searchweb.book.service;

import com.atin.searchweb.book.dto.NaverBooksDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NaverBookApiService {

	@GET("/v1/search/book.json")
	Call<NaverBooksDto> getBooks(@Query("query") String query, @Query("start") Integer start, @Query("display") Integer display);

}
