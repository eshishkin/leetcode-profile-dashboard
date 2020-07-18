package org.eshishkin.leetcode.config;

import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.webserver.Routing;
import lombok.AllArgsConstructor;
import org.eshishkin.leetcode.service.LeetcodeProfileResource;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
@AllArgsConstructor(onConstructor_= @Inject)
public class RouterConfigProvider implements Provider<Routing> {
    private final LeetcodeProfileResource leetcodeProfileResource;

    @Override
    public Routing get() {
        return Routing.builder()
                .register(this::healthChecks)
                .register(leetcodeProfileResource)
                .build();
    }

    private HealthSupport healthChecks() {
        return HealthSupport.builder()
                .addLiveness(HealthChecks.healthChecks())
                .build();
    }
}
