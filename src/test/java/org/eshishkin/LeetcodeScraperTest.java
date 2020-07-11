package org.eshishkin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LeetcodeScraperTest {

    @Test
    public void test() throws IOException {
        Document document = Jsoup.connect("https://leetcode.com/ocmwdt").get();
        String realname = document.select(".realname").first().text();
        String username = document.select(".username").first().text();

        String solved = document
                .getElementsContainingOwnText("Solved Question")
                .select(".badge")
                .first()
                .text();

        String acceptance = document
                .getElementsContainingOwnText("Acceptance Rate")
                .select(".badge")
                .first()
                .text();

        String submissions = document
                .getElementsContainingOwnText("Accepted Submission")
                .select(".badge")
                .first()
                .text();

        System.out.println("String name: " + realname);
        System.out.println("String username: " + username);

        System.out.println("Solved Question: " + solved);
        System.out.println("Acceptance Rate: " + acceptance);
        System.out.println("Accepted Submission: " + submissions);

        System.out.println();

        System.out.println("Last problems");

        System.out.println();
        document.select("a[href*=/problems]").forEach(e -> {
            System.out.println("Name: " + e.child(2).text());
            System.out.println("When: " + e.selectFirst(".text-muted").text());
            System.out.println("Link: " + e.attr("href"));
            System.out.println("Info: " + e.selectFirst(".progress-bar-info").text());

            Optional.ofNullable(e.selectFirst(".progress-bar-danger"))
                    .ifPresent(v -> System.out.println("Failed: " + v));

            Optional.ofNullable(e.selectFirst(".progress-bar-success"))
                    .ifPresent(v -> System.out.println("Accepted: Yes"));

            System.out.println();
        });
    }
}
