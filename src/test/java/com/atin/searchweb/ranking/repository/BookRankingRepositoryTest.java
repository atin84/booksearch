package com.atin.searchweb.ranking.repository;

import com.atin.searchweb.ranking.domain.BookRanking;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRankingRepositoryTest {

	/*@Autowired
	private BookRankingRepository bookRankingRepository;

	@Test
	public void save___기본_등록_조회___success() {
		//given
		String id = "testid";
		LocalDateTime refreshTime = LocalDateTime.of(2018, 5, 26, 0, 0);
		BookRanking point = BookRanking.builder()
				.id(id)
				.amount(1000L)
				.refreshTime(refreshTime)
				.build();

		//when
		bookRankingRepository.save(point);

		//then
		BookRanking bookRanking = bookRankingRepository.findById(id).get();
		assertEquals(1000L, bookRanking.getAmount().longValue());
		assertEquals(bookRanking.getRefreshTime(), refreshTime);
	}*/
}
