package org.eshishkin.leetcode.external;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import org.eshishkin.leetcode.model.LeetcodeProfile;
import org.eshishkin.leetcode.model.LeetcodeProfile.Problem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Singleton
@AllArgsConstructor(onConstructor_= @Inject)
public class ExtLeetcodeService {
    private static final Pattern ACCEPTANCE_PATTERN = Pattern.compile("(\\d*) / (.*)");

    private final LeetcodeClient client;

    public CompletableFuture<LeetcodeProfile> getProfile(String userId) {
        return client
                .getProfile(userId)
                .thenApply(Jsoup::parse)
                .thenApply(this::toProfile);
    }

    private LeetcodeProfile toProfile(Document document) {
        String name = document.select(".realname").first().text();
        String username = document.select(".username").first().text();

        String solved = document
                .getElementsContainingOwnText("Solved Question")
                .select(".badge")
                .first()
                .text();

        String submissions = document
                .getElementsContainingOwnText("Accepted Submission")
                .select(".badge")
                .first()
                .text();

        LeetcodeProfile profile = new LeetcodeProfile();
        profile.setName(name);
        profile.setUsername(username);
        profile.setSolved(getSolved(solved));
        profile.setAccepted(getAccepted(submissions));
        profile.setSubmitted(getTotalSubmissions(submissions));
        profile.setLatestProblems(document
                .select("a[href*=/problems]")
                .stream()
                .map(e -> {
                    Problem problem = new Problem();
                    problem.setName(e.child(2).text());
                    problem.setUrl(e.attr("href"));
                    problem.setInfo(e.selectFirst(".progress-bar-info").text());
                    problem.setAccepted(e.selectFirst(".progress-bar-success") != null);
                    return problem;
                })
                .collect(Collectors.toList())
        );

        return profile;
    }

    private Integer getAccepted(String submissions) {
        Matcher matcher = ACCEPTANCE_PATTERN.matcher(submissions);
        return matcher.matches() ? Integer.valueOf(matcher.group(1)) : null;
    }

    private Integer getTotalSubmissions(String submissions) {
        Matcher matcher = ACCEPTANCE_PATTERN.matcher(submissions);
        return matcher.matches() ? Integer.valueOf(matcher.group(2)) : null;
    }

    private Integer getSolved(String data) {
        Matcher matcher = ACCEPTANCE_PATTERN.matcher(data);
        return matcher.matches() ? Integer.valueOf(matcher.group(1)) : null;
    }
}
