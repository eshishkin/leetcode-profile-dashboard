package org.eshishkin.leetcode.external;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

@Singleton
public class LeetcodeClient {

    private final HttpClient client;

    @Inject
    public LeetcodeClient(HttpClient.Builder builder) {
        client = builder.build();
    }

    public CompletableFuture<String> getProfile(String userId) {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create("https://leetcode.com/" + userId))
                .timeout(Duration.ofMinutes(1))
                .GET()
                .build();

        return client
                .sendAsync(request, ofString())
                .thenApply(r -> {
                    if (r.statusCode() == 404) {
                        throw new RuntimeException("Profile not found");
                    } else if (r.statusCode() > 400) {
                        throw new RuntimeException("Unexpected error:" + r.body());
                    }
                    return r.body();
                });
    }
}
