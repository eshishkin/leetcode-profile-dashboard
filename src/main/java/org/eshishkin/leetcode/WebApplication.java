package org.eshishkin.leetcode;

import io.helidon.common.reactive.CompletionAwaitable;
import io.helidon.config.Config;
import io.helidon.media.jackson.JacksonSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
@AllArgsConstructor(onConstructor_= @Inject)
public class WebApplication {

    private final Config config;
    private final Routing routing;

    public CompletionAwaitable<Void> start() {
        final long now = System.currentTimeMillis();

        return WebServer
                .builder()
                .addMediaSupport(JacksonSupport.create())
                .config(config.get("server"))
                .routing(routing)
                .build()
                .start()
                .thenAccept(server -> {
                    log.info(String.format(
                            "Server started at: http://localhost:{} (~{} ms)",
                            server.port(), System.currentTimeMillis() - now
                    ));
                });
    }
}
