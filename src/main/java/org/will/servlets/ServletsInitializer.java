package org.will.servlets;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.will.Utils.LoggerUtil;
import org.will.Utils.PackageScanner;
import org.will.annotation.RequestMapping;
import org.will.exception.CustomException;
import org.will.filter.AuthenticationFilter;
import org.will.server.servlets.register.ServletRegister;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class ServletsInitializer {

    static ServletRegister servletRegister = new ServletRegister();

    public static void initializeServlets() {

        LoggerUtil loggerUtil = new LoggerUtil();
        List<Class<? extends AbstractServlet>> servlets = PackageScanner.getAbstractServletClassesInPackage();

        Logger logger = loggerUtil.getLogger(ServletsInitializer.class);
        servlets.forEach(clazz -> {
            try {
                String path = clazz.getAnnotation(RequestMapping.class).path();

                servletRegister.register(clazz.getDeclaredConstructor().newInstance(), path);
                servletRegister.register(clazz.getDeclaredConstructor().newInstance(), path + "/*");
                logger.info("Servlet: " + clazz.getName());
                logger.info("Path: " + path);

            } catch (Exception e) {
                throw new CustomException("Erro ao configurar servlet: " + e.getMessage());
            }
        });
    }

    public static ServletContextHandler getContext() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setContextPath("/");

        Map<AbstractServlet, String> servlets = servletRegister.getServlets();

        FilterHolder filterHolder = new FilterHolder(AuthenticationFilter.class);

        context.addFilter(filterHolder, "/*", EnumSet.of(DispatcherType.REQUEST));

        servlets.forEach((servlet, path) -> {
            ServletHolder holder = new ServletHolder(servlet);
            context.addServlet(holder, path);
        });
        return context;
    }

}
