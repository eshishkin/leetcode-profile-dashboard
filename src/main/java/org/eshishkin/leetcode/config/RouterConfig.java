package org.eshishkin.leetcode.config;

import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.webserver.Routing;
import org.eshishkin.leetcode.service.HelloWorldResource;
import org.eshishkin.leetcode.service.LeetcodeProfileResource;

public class RouterConfig {

    public Routing routing() {
        return Routing.builder()
                .register(this::healthChecks)
                .register(HelloWorldResource::new)
                .register(LeetcodeProfileResource::new)
                .build();
    }

    private HealthSupport healthChecks() {
        return HealthSupport.builder()
                .addLiveness(HealthChecks.healthChecks())
                .build();
    }
}
