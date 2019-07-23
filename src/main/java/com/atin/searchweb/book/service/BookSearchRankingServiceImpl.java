package com.atin.searchweb.book.service;

import com.atin.searchweb.book.dto.BookRankingDto;
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
public class BookSearchRankingServiceImpl implements BookSearchRankingService {

	@Value("${redis-key.ranking.search.book}")
	private String bookSearchRankingKey;

	private final RedisTemplate<String, String> redisTemplate;

	private ZSetOperations<String, String> zSetOperations;

	@PostConstruct
	public void init() {
		zSetOperations = redisTemplate.opsForZSet();
	}

	@Override
	public void incrementBookSearchScore(String keyword) {
		zSetOperations.incrementScore(bookSearchRankingKey, keyword, 1);
	}

	@Override
	public List<BookRankingDto> getBookSearchRank() {
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
