package org.will.server.servlets.register;

import org.will.servlets.AbstractServlet;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ServletRegister {

    private final Map<AbstractServlet, String> servlets = new HashMap<>();

    public void register(AbstractServlet servletHolder, String path) {
        servlets.putIfAbsent(servletHolder, path);
    }

    public Map<AbstractServlet, String> getServlets() {
        return this.servlets;
    }

}
