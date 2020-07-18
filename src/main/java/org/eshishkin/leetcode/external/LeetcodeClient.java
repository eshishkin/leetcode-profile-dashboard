package org.eshishkin.leetcode.external;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

@Singleton
public class LeetcodeClient {

    private final HttpClient client;

    @Inject
    public LeetcodeClient(HttpClient.Builder builder) {
        client = builder.build();
    }

    public String getProfile(String userId) {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://leetcode.com/" + userId))
                .timeout(Duration.ofMinutes(1))
                .GET()
                .build();

        return client.sendAsync(request, ofString()).join().body();
    }
}
