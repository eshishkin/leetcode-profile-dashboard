package org.eshishkin.leetcode;

import io.helidon.config.Config;
import io.helidon.media.jackson.JacksonSupport;
import io.helidon.webserver.WebServer;
import org.eshishkin.leetcode.config.RouterConfig;

import java.util.concurrent.atomic.AtomicLong;

public class Application {

    public static void main(String[] args) {
        Config config = Config.create();
        final long now = System.currentTimeMillis();

        WebServer.builder()
                .addMediaSupport(JacksonSupport.create())
                .config(config.get("server"))
                .routing(() -> new RouterConfig().routing())
                .build()
                .start()
                .thenAccept(server -> {

                    System.out.println(String.format(
                            "Server started at: http://localhost:%s (%s ms)",
                            server.port(), System.currentTimeMillis() - now
                    ));
                });
    }
}
