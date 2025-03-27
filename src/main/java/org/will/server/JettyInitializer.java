package org.will.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.will.exception.CustomErrorHandler;
import org.will.servlets.ServletsInitializer;

public class JettyInitializer {

    public static void start() {
        Server server = new Server(7272);

        ServletsInitializer.initializeServlets();

        ServletContextHandler context = ServletsInitializer.getContext();

        CustomErrorHandler customErrorHandler = new CustomErrorHandler();

        context.setErrorHandler(customErrorHandler);

        context.setInitParameter("org.eclipse.jetty.servlet.Default.charset", "UTF-8");

        HandlerCollection handlers = new HandlerCollection();

        handlers.addHandler(context);

        server.setHandler(handlers);

        init(server);
    }

    private static void init(Server server) {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
