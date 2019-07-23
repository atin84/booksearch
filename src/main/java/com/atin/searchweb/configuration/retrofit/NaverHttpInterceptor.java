package com.atin.searchweb.configuration.retrofit;

import okhttp3.Interceptor;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NaverHttpInterceptor implements Interceptor {

	@Value("${api.naver.client-id}")
	private String naverClientId;

	@Value("${api.naver.client-secret}")
	private String naverClientSecret;

	@Override
	public Response intercept(Chain chain) throws IOException {
		return chain.proceed(
				chain.request().newBuilder()
						.addHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
						.addHeader("Cache-Control", "no-cache")
						.addHeader("X-Naver-Client-Id", naverClientId)
						.addHeader("X-Naver-Client-Secret", naverClientSecret)
						.build()
		);
	}
}
