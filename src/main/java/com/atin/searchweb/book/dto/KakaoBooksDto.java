package com.atin.searchweb.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class KakaoBooksDto {

	@JsonProperty("documents")
	private List<KakaoBookDto> documents;

	@JsonProperty("meta")
	private KakaoMetaDto meta;
}
