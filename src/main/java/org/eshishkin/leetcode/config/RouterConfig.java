package org.eshishkin.leetcode.config;

import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.webserver.Routing;
import org.eshishkin.leetcode.HelloWorldResource;

public class RouterConfig {

    public Routing routing() {
        return Routing.builder()
//                .register(JsonSupport.create())
                .register(this::healthChecks)
                .register(HelloWorldResource::new)
                .build();
    }

    private HealthSupport healthChecks() {
        return HealthSupport.builder()
                .addLiveness(HealthChecks.healthChecks())
                .build();
    }
}
