package org.example.servlets;

import org.example.Constants.Constants;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;

public abstract class AbstractServlet extends HttpServlet {

    public static String getPath(Class<? extends AbstractServlet> clazz) {

        String path;
        try {
            Field field = clazz.getDeclaredField(Constants.PATH);
            field.setAccessible(Boolean.TRUE);
            path = (String)field.get(null);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("A classe " + clazz.getName() + " não tem um path: " + e.getMessage());
        }

        return path;
    }

    public static void setConfigs(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

}

