package org.eshishkin.leetcode.service;

import io.helidon.webserver.Routing;
import io.helidon.webserver.Service;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AllArgsConstructor;
import org.eshishkin.leetcode.external.ExtLeetcodeService;

@Singleton
@AllArgsConstructor(onConstructor_= @Inject)
public class LeetcodeProfileMetricResource implements Service {

    private static final String NEW_LINE = "\n";

    private final ExtLeetcodeService service;

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/profile/{userId}/metrics", (req, res) -> {
            String userId = req.path().param("userId");
            service.getProfile(userId)
                    .thenAccept(profile -> {
                        StringBuilder data = new StringBuilder();

                        data.append(createMetric("leetcode_solved", profile.getSolved(), userId));
                        data.append(NEW_LINE);
                        data.append(createMetric("leetcode_accepted", profile.getAccepted(), userId));
                        data.append(NEW_LINE);
                        data.append(createMetric("leetcode_submitted", profile.getSubmitted(), userId));
                        data.append(NEW_LINE);

                        res.send(data.toString());
                    })
                    .exceptionally(ex -> {
                        req.next(ex);
                        return null;
                    });
        });
    }

    private String createMetric(String name, Object value, String profile) {
        return String.format("%s{profile=\"%s\"} %s", name, profile, value);
    }
}
