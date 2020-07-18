package org.eshishkin.leetcode.config;

import lombok.AllArgsConstructor;

import javax.inject.Provider;
import javax.inject.Singleton;
import java.net.http.HttpClient;
import java.time.Duration;

@Singleton
@AllArgsConstructor
public class HttpClientBuilderProvider implements Provider<HttpClient.Builder> {

    @Override
    public HttpClient.Builder get() {
        return HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(30));
    }
}
