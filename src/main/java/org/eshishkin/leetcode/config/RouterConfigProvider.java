package org.eshishkin.leetcode.config;

import io.helidon.common.http.Http.Status;
import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.metrics.MetricsSupport;
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
                .register(healthChecks())
                .register(metrics())
                .register(leetcodeProfileResource)
                .any((req, res) -> {
                    res.status(Status.NOT_FOUND_404);
                    res.send();
                })
                .error(Throwable.class, (req, res, ex) -> {
                    res.status(Status.INTERNAL_SERVER_ERROR_500);
                    res.send(ex.getMessage());
                })
                .build();
    }

    private HealthSupport healthChecks() {
        return HealthSupport.builder()
                .addLiveness(HealthChecks.healthChecks())
                .build();
    }

    private MetricsSupport metrics() {
        return MetricsSupport.builder().build();
    }
}
