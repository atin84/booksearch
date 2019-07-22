package com.atin.searchweb.ranking.service;

import com.atin.searchweb.ranking.dto.BookRankingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookRankingService {

	@Value("${redis-key.ranking.search.book}")
	private String bookSearchRankingKey;

	private final RedisTemplate<String, String> redisTemplate;

	private ZSetOperations<String, String> zSetOperations;

	@PostConstruct
	public void init() {
		zSetOperations = redisTemplate.opsForZSet();
	}

	public void incrementBookSearchScore(String keyword) {
		zSetOperations.incrementScore(bookSearchRankingKey, keyword, 1);
	}

	public List<BookRankingDto> getBookSearchRank() {
		//zSetOperations.rank(KEY_BOOK_SEARCH_RANKING);
		Set<ZSetOperations.TypedTuple<String>> rankingSet = zSetOperations.reverseRangeWithScores(bookSearchRankingKey, 0, 10);

		return rankingSet.stream()
				.map(item -> {
					BookRankingDto bookRankingDto = new BookRankingDto();
					bookRankingDto.setCount(item.getScore().longValue());
					bookRankingDto.setKeyword(item.getValue());
					return bookRankingDto;
				})
				.collect(Collectors.toList());
	}


}
