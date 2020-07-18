package org.eshishkin.leetcode;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import org.eshishkin.leetcode.config.ApplicationBindingModule;

@Slf4j
public class Application {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ApplicationBindingModule());
        WebApplication server = injector.getInstance(WebApplication.class);
        server.start();
    }
}
