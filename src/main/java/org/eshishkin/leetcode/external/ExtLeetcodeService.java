package org.eshishkin.leetcode.external;

import lombok.AllArgsConstructor;
import org.eshishkin.leetcode.model.LeetcodeProfile;
import org.eshishkin.leetcode.model.LeetcodeProfile.Problem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.stream.Collectors;

@AllArgsConstructor
public class ExtLeetcodeService {
    private LeetcodeClient client;

    public LeetcodeProfile getProfile(String userId) {
        String html = client.getProfile(userId);
        return toProfile(Jsoup.parse(html));
    }

    private LeetcodeProfile toProfile(Document document) {
        String name = document.select(".realname").first().text();
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

        LeetcodeProfile profile = new LeetcodeProfile();
        profile.setName(name);
        profile.setUsername(username);
        profile.setSolved(solved);
        profile.setAccepted(submissions);
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
}
