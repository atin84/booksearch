package com.atin.searchweb.ranking.service;

import com.atin.searchweb.ranking.dto.BookRankingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookRankingService {

	public static final String KEY_BOOK_SEARCH_RANKING = "book_search_ranking";

	private final RedisTemplate<String, String> redisTemplate;

	private ZSetOperations<String, String> zSetOperations;

	@PostConstruct
	public void init() {
		zSetOperations = redisTemplate.opsForZSet();
	}

	public void incrementBookSearchScore(String keyword) {
		zSetOperations.incrementScore(KEY_BOOK_SEARCH_RANKING, keyword, 1);
	}

	public List<BookRankingDto> getBookSearchRank() {
		//zSetOperations.rank(KEY_BOOK_SEARCH_RANKING);
		Set<ZSetOperations.TypedTuple<String>> rankingSet = zSetOperations.reverseRangeWithScores(KEY_BOOK_SEARCH_RANKING, 0, 10);

		return rankingSet.stream()
				.map(item -> {
					BookRankingDto bookRankingDto = new BookRankingDto();
					bookRankingDto.setCount(item.getScore().longValue());
					bookRankingDto.setKeyword(item.getValue());
					return bookRankingDto;
				})
				.collect(Collectors.toList());
	}

	/*public List<String> getPlayersRankOfRange(int startIndex, int endIndex) {
		Set<String> rankReverseSet = zSetOperations.reverseRange(KEY_RANKING, startIndex, endIndex);
		Iterator<String> iter = rankReverseSet.iterator();
		List<String> list = new ArrayList<>(rankReverseSet.size());

		while (iter.hasNext()) {
			list.add(iter.next());
		}

		return list;
	}

	/*public PlayerRankModel getOnePlayerRank(String nickname) {
		Long playerRank = zSetOperations.reverseRank(KEY_RANKING, nickname);

		if (playerRank == null) {
			throw new NotFoundException("Please check nickname");
		}

		PlayerRankModel playerRankModel = new PlayerRankModel();
		playerRankModel.setNickname(nickname);
		playerRankModel.setRank(playerRank.longValue() + 1);
		playerRankModel.setScore(zSetOperations.score(KEY, nickname));

		//zSetOperations.rank();

		return playerRankModel;
	}

	public void updateLeaderBoard(String winner, double winnerScore, String loser, double loserScore) {
		zSetOperations.add(KEY, winner, winnerScore);
		zSetOperations.add(KEY, loser, loserScore);
	}*/


}
