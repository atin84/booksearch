package com.atin.searchweb.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MetaDto {

	// 전체 검색된 문서수
	@JsonProperty("total_count")
	private Integer totalCount;

	// 검색결과로 제공 가능한 문서수
	@JsonProperty("pageable_count")
	private Integer pageableCount;

	// 현재 페이지가 마지막 페이지인지 여부. 값이 false이면 page를 증가시켜 다음 페이지를 요청할 수 있음
	@JsonProperty("is_end")
	private Boolean isEnd;
}
