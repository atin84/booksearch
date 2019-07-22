package com.atin.searchweb.configuration;

import com.atin.searchweb.book.service.KakaoApiService;
import com.atin.searchweb.book.service.NaverApiService;
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
	private Interceptor kakaoHttpInterceptor;

	@Autowired
	private Interceptor naverHttpInterceptor;

	@Value("${api.kakao.url}")
	private String kakaoApiUrl;

	@Value("${api.naver.url}")
	private String naverApiUrl;

	@Bean("kakaoJsonOkHttpClient")
	public OkHttpClient kakaoJsonOkHttpClient() {
		return new OkHttpClient.Builder()
				.addInterceptor(kakaoHttpInterceptor)
				.build();
	}

	@Bean("naverJsonOkHttpClient")
	public OkHttpClient naverJsonOkHttpClient() {
		return new OkHttpClient.Builder()
				.addInterceptor(naverHttpInterceptor)
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
			@Qualifier("kakaoJsonOkHttpClient") OkHttpClient jsonPlaceholderOkHttpClient
	) {
		return new Retrofit.Builder()
				.baseUrl(kakaoApiUrl)
				.addConverterFactory(JacksonConverterFactory.create(jsonPlaceholderObjectMapper))
				.client(jsonPlaceholderOkHttpClient)
				.build();
	}

	@Bean("naverApiRetrofit")
	public Retrofit naverApiRetrofit(
			@Qualifier("jsonPlaceholderObjectMapper") ObjectMapper jsonPlaceholderObjectMapper,
			@Qualifier("naverJsonOkHttpClient") OkHttpClient jsonPlaceholderOkHttpClient
	) {
		return new Retrofit.Builder()
				.baseUrl(naverApiUrl)
				.addConverterFactory(JacksonConverterFactory.create(jsonPlaceholderObjectMapper))
				.client(jsonPlaceholderOkHttpClient)
				.build();
	}

	@Bean("kakaoApiService")
	public KakaoApiService kakaoApiService(@Qualifier("kakaoApiRetrofit") Retrofit kakaoApiRetrofit) {
		return kakaoApiRetrofit.create(KakaoApiService.class);
	}

	@Bean("naverApiService")
	public NaverApiService naverApiService(@Qualifier("naverApiRetrofit") Retrofit naverApiRetrofit) {
		return naverApiRetrofit.create(NaverApiService.class);
	}
}
