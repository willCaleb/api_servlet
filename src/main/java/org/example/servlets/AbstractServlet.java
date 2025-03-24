package org.example.servlets;

import org.example.Constants.Constants;
import org.example.Utils.GsonBuilder;
import org.example.entity.AbstractEntity;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractServlet<E extends AbstractEntity> extends HttpServlet {

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

    @SuppressWarnings("unchecked")
    public E toEntityFromRequest(HttpServletRequest request) {
        return (E) GsonBuilder.toEntityFromRequest(request, getEntityClass());
    }

    @SuppressWarnings("unchecked")
    public Class<E> getEntityClass() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        return (Class<E>) genericTypes[0];
    }

    public static void setConfigs(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    public String toJson(Object object) {
        return GsonBuilder.getGjon().toJson(object);
    }
}

