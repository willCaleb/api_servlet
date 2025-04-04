package org.will.servlets;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.will.Utils.PackageScanner;
import org.will.annotation.RequestMapping;
import org.will.filter.AuthenticationFilter;
import org.will.server.servlets.register.ServletRegister;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class ServletsInitializer {

    static ServletRegister servletRegister = new ServletRegister();

    public static void initializeServlets() {


        List<Class<? extends AbstractServlet>> classesInPackage = PackageScanner.getAbstractServletClassesInPackage(PeopleServlet.class.getPackage().getName());

        classesInPackage.forEach(clazz -> {
            try {
                String path = clazz.getAnnotation(RequestMapping.class).path();

                servletRegister.register(clazz.getDeclaredConstructor().newInstance(), path);
                servletRegister.register(clazz.getDeclaredConstructor().newInstance(), path + "/*");
            }catch (Exception e) {
                throw new RuntimeException("Erro ao configurar servlet: " + e.getMessage());
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

        FilterHolder filterHolder = new FilterHolder(AuthenticationFilter.class);

        context.addFilter(filterHolder, "/secure", EnumSet.of(DispatcherType.REQUEST));

        servlets.forEach((servlet, path)  -> {
            ServletHolder holder = new ServletHolder(servlet);
            context.addServlet(holder, path);
        });
        return context;
    }

}
