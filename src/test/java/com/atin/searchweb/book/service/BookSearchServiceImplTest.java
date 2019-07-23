package com.atin.searchweb.book.service;

import com.atin.searchweb.book.dto.*;
import okhttp3.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookSearchServiceImplTest {

	@Mock
	private KakaoBookApiService kakaoApiService;

	@Mock
	private NaverBookApiService naverApiService;

	@Mock
	private BookSearchHistoryService bookSearchHistoryService;

	@Mock
	private BookSearchRankingService bookSearchRankingService;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private BookSearchServiceImpl bookSearchService;

	@Test(expected = RuntimeException.class)
	public void searchBooks___API_ERROR___RuntimeException() throws Exception {
		// given
		BookSearchRequestDto bookSearchRequestDto = new BookSearchRequestDto();
		bookSearchRequestDto.setPage(1);
		bookSearchRequestDto.setSize(10);
		bookSearchRequestDto.setTitle("title");
		bookSearchRequestDto.setId("id");

		Call<KakaoBooksDto> kakaoBooksDtoCall = mock(Call.class);
		Response<KakaoBooksDto> kakaoBooksDtoResponse = Response.error(500, ResponseBody.create(okhttp3.MediaType.parse(MediaType.APPLICATION_JSON_VALUE), "content"));

		when(kakaoApiService.getBooks(anyString(), anyInt(), anyInt())).thenReturn(kakaoBooksDtoCall);
		when(kakaoBooksDtoCall.execute()).thenReturn(kakaoBooksDtoResponse);

		// when
		bookSearchService.searchBooks(bookSearchRequestDto);
	}

	@Test
	public void searchBooks___API_SUCCESS___SUCCESS() throws Exception {
		// given
		BookSearchRequestDto bookSearchRequestDto = new BookSearchRequestDto();
		bookSearchRequestDto.setPage(1);
		bookSearchRequestDto.setSize(10);
		bookSearchRequestDto.setTitle("title");
		bookSearchRequestDto.setId("id");

		KakaoMetaDto kakaoMetaDto = new KakaoMetaDto();
		kakaoMetaDto.setPageableCount(10);

		KakaoBooksDto kakaoBooksDto = new KakaoBooksDto();
		kakaoBooksDto.setDocuments(Arrays.asList(new KakaoBookDto()));
		kakaoBooksDto.setMeta(kakaoMetaDto);

		Call<KakaoBooksDto> kakaoBooksDtoCall = mock(Call.class);
		Response<KakaoBooksDto> kakaoBooksDtoResponse = Response.success(kakaoBooksDto);

		when(kakaoApiService.getBooks(anyString(), anyInt(), anyInt())).thenReturn(kakaoBooksDtoCall);
		when(kakaoBooksDtoCall.execute()).thenReturn(kakaoBooksDtoResponse);

		// when
		Page<BookDto> bookDtoPage = bookSearchService.searchBooks(bookSearchRequestDto);

		// then
		assertNotNull(bookDtoPage);
		verify(kakaoApiService, times(1)).getBooks(anyString(), anyInt(), anyInt());
		verify(naverApiService, times(0)).getBooks(anyString(), anyInt(), anyInt());
		verify(bookSearchHistoryService, times(1)).addBookSearchHistory(anyString(), any());
		verify(bookSearchRankingService, times(1)).incrementBookSearchScore(anyString());
	}

	@Test
	public void getFallback() throws Exception {
		// given
		BookSearchRequestDto bookSearchRequestDto = new BookSearchRequestDto();
		bookSearchRequestDto.setPage(1);
		bookSearchRequestDto.setSize(10);
		bookSearchRequestDto.setTitle("title");
		bookSearchRequestDto.setId("id");

		NaverBooksDto naverBooksDto = new NaverBooksDto();
		naverBooksDto.setItems(Arrays.asList(new NaverBookDto()));
		naverBooksDto.setTotal(11);

		Call<NaverBooksDto> naverBooksDtoCall = mock(Call.class);
		Response<NaverBooksDto> naverBooksDtoResponse = Response.success(naverBooksDto);

		when(naverApiService.getBooks(anyString(), anyInt(), anyInt())).thenReturn(naverBooksDtoCall);
		when(naverBooksDtoCall.execute()).thenReturn(naverBooksDtoResponse);

		// when
		Page<BookDto> bookDtoPage = bookSearchService.getFallback(bookSearchRequestDto);

		// then
		assertNotNull(bookDtoPage);
		verify(kakaoApiService, times(0)).getBooks(anyString(), anyInt(), anyInt());
		verify(naverApiService, times(1)).getBooks(anyString(), anyInt(), anyInt());
		verify(bookSearchHistoryService, times(1)).addBookSearchHistory(anyString(), any());
		verify(bookSearchRankingService, times(1)).incrementBookSearchScore(anyString());
	}
}