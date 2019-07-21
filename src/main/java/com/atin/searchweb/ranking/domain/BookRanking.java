package com.atin.searchweb.ranking.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Builder
@Getter
@RedisHash("book_ranking")
public class BookRanking {

	@Id
	private String id;

	private Long amount;

	private LocalDateTime refreshTime;

	public void refresh(long amount, LocalDateTime refreshTime) {
		if (refreshTime.isAfter(this.refreshTime)) { // 저장된 데이터보다 최신 데이터일 경우
			this.amount = amount;
			this.refreshTime = refreshTime;
		}
	}

}
