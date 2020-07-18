package org.eshishkin.leetcode.config;

import com.google.inject.AbstractModule;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;

import java.net.http.HttpClient;

public class ApplicationBindingModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Routing.class).toProvider(RouterConfigProvider.class);
        bind(Config.class).toProvider(Config::create).asEagerSingleton();
        bind(HttpClient.Builder.class).toProvider(HttpClientBuilderProvider.class);
    }
}
