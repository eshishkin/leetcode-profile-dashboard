package org.eshishkin.leetcode;

import io.helidon.webserver.Routing;
import io.helidon.webserver.Service;

public class HelloWorldResource implements Service {

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/greet", (req, res) -> res.send(hello()));
    }

    private String hello() {
        return "Hello World";
    }
}
