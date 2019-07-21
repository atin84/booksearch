package com.atin.searchweb.configuration;

import com.atin.searchweb.book.service.KakaoApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfig {

	@Autowired
	private Interceptor KakaoHttpInterceptor;

	@Value("${api.kakao.url}")
	private String kakaoApiUrl;

	@Bean("jsonPlaceholderOkHttpClient")
	public OkHttpClient jsonPlaceholderOkHttpClient() {
		return new OkHttpClient.Builder()
				.addInterceptor(KakaoHttpInterceptor)
				.build();
	}

	@Bean("jsonPlaceholderObjectMapper")
	public ObjectMapper jsonPlaceholderObjectMapper() {
		return Jackson2ObjectMapperBuilder.json()
				.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
				.modules(new JavaTimeModule())
				.build();
	}

	@Bean("kakaoApiRetrofit")
	public Retrofit kakaoApiRetrofit(
			@Qualifier("jsonPlaceholderObjectMapper") ObjectMapper jsonPlaceholderObjectMapper,
			@Qualifier("jsonPlaceholderOkHttpClient") OkHttpClient jsonPlaceholderOkHttpClient
	) {
		return new Retrofit.Builder()
				.baseUrl(kakaoApiUrl)
				.addConverterFactory(JacksonConverterFactory.create(jsonPlaceholderObjectMapper))
				.client(jsonPlaceholderOkHttpClient)
				.build();
	}

	@Bean("kakaoApiService")
	public KakaoApiService jsonPlaceholderService(@Qualifier("kakaoApiRetrofit") Retrofit kakaoApiRetrofit) {
		return kakaoApiRetrofit.create(KakaoApiService.class);
	}
}
