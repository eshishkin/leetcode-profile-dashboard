package org.eshishkin.leetcode.external;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

import static java.net.http.HttpResponse.BodyHandlers.ofString;

public class LeetcodeClient {
    private HttpClient client;

    public LeetcodeClient() {
        this.client = HttpClient
                .newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(30))
                .build();
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
