package com.atin.searchweb.book.service;

import com.atin.searchweb.book.dto.KakaoBooksDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface KakaoBookApiService {

	@GET("/v3/search/book?target=title")
	Call<KakaoBooksDto> getBooks(@Query("query") String query, @Query("page") Integer page, @Query("size") Integer size);

}
