package org.eshishkin.leetcode.service;

import io.helidon.webserver.Routing;
import io.helidon.webserver.Service;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AllArgsConstructor;
import org.eshishkin.leetcode.external.ExtLeetcodeService;

@Singleton
@AllArgsConstructor(onConstructor_= @Inject)
public class LeetcodeProfileResource implements Service {

    private final ExtLeetcodeService service;

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/profile/{userId}", (req, res) -> {
            String userId = req.path().param("userId");
            service.getProfile(userId).thenAccept(res::send).exceptionally(ex -> {
                req.next(ex);
                return null;
            });
        });
    }
}
