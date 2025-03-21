package org.example.server.servlets;

import org.example.servlets.AbstractServlet;

import java.util.HashMap;
import java.util.Map;

public class ServletRegister {

    private Map<AbstractServlet, String> servlets = new HashMap<>();

    public void register(AbstractServlet servletHolder, String path) {
        servlets.putIfAbsent(servletHolder, path);
    }

    public Map<AbstractServlet, String> getServlets() {
        return this.servlets;
    }

}
