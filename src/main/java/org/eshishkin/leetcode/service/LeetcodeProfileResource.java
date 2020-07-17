package org.eshishkin.leetcode.service;

import io.helidon.webserver.Routing;
import io.helidon.webserver.Service;
import org.eshishkin.leetcode.external.ExtLeetcodeService;
import org.eshishkin.leetcode.external.LeetcodeClient;
import org.eshishkin.leetcode.model.LeetcodeProfile;

public class LeetcodeProfileResource implements Service {

    private ExtLeetcodeService service;

    public LeetcodeProfileResource() {
        this.service = new ExtLeetcodeService(new LeetcodeClient());
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/profile/{userId}", (req, res) -> {
            String userId = req.path().param("userId");
            LeetcodeProfile profile = service.getProfile(userId);
            res.send(profile);
        });

    }
}
