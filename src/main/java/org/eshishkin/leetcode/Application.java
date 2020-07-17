package org.eshishkin.leetcode;

import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import org.eshishkin.leetcode.config.RouterConfig;

public class Application {

    public static void main(String[] args) {
        Config config = Config.create();
        Routing routing = new RouterConfig().routing();

        WebServer.create(routing, config.get("server"))
                .start()
                .thenAccept(server -> System.out.println("Server started at: http://localhost:" + server.port()));

    }
}
