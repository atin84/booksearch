package com.atin.searchweb.ranking.repository;

import com.atin.searchweb.ranking.domain.BookRanking;
import org.springframework.data.repository.CrudRepository;

public interface BookRankingRepository extends CrudRepository<BookRanking, String> {
}
