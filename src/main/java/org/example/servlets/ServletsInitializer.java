package org.example.servlets;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.Constants.Constants;
import org.example.Utils.PackageScanner;
import org.example.entity.Pessoa;
import org.example.server.servlets.ServletRegister;

import java.util.List;
import java.util.Map;

public class ServletsInitializer {

    static ServletRegister servletRegister = new ServletRegister();

    public static void initializeServlets() {


        List<Class<? extends AbstractServlet>> classesInPackage = PackageScanner.getClassesInPackage(PessoaServlet.class.getPackage().getName());

        classesInPackage.forEach(clazz -> {
            try {
                String path = AbstractServlet.getPath(clazz);

                servletRegister.register(clazz.getDeclaredConstructor().newInstance(), path);
                servletRegister.register(clazz.getDeclaredConstructor().newInstance(), path + "/*");
            }catch (Exception e) {

            }
        });

        servletRegister.getServlets().forEach((s, i) -> {
            System.out.println("Servlet: " + s);
            System.out.println("Path: " + i);
        });
    }

    public static ServletContextHandler getContext() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setContextPath("/");

        Map<AbstractServlet, String> servlets = servletRegister.getServlets();

        servlets.forEach((servlet, path)  -> {
            ServletHolder holder = new ServletHolder(servlet);
            context.addServlet(holder, path);
        });
        return context;
    }

}
