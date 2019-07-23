package com.atin.searchweb.book.service;

import com.atin.searchweb.book.dto.BookRankingDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookSearchRankingServiceImplTest {

	@Mock
	private RedisTemplate<String, String> redisTemplate;

	@Mock
	private ZSetOperations<String, String> zSetOperations;

	@InjectMocks
	private BookSearchRankingServiceImpl bookSearchRankingService;

	@Before
	public void before() {
		ReflectionTestUtils.setField(bookSearchRankingService, "bookSearchRankingKey", "key");
		when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
		bookSearchRankingService.init();
	}

	@Test
	public void incrementBookSearchScore___default___success() {
		// when
		bookSearchRankingService.incrementBookSearchScore("keyword");

		// then
		verify(zSetOperations, times(1)).incrementScore(anyString(), anyString(), anyDouble());
	}

	@Test(expected = IllegalArgumentException.class)
	public void incrementBookSearchScore___empty_id___exception() {
		// when
		bookSearchRankingService.incrementBookSearchScore(" ");
	}

	@Test
	public void getBookSearchRank___default___success() {
		// given
		Set<ZSetOperations.TypedTuple<String>> rankingSetMock = mock(Set.class);
		when(zSetOperations.reverseRangeWithScores(anyString(), anyLong(), anyLong())).thenReturn(rankingSetMock);

		// when
		List<BookRankingDto> bookRankingDtoList = bookSearchRankingService.getBookSearchRank();

		// then
		assertNotNull(bookRankingDtoList);
		assertEquals(0, bookRankingDtoList.size());
	}
}