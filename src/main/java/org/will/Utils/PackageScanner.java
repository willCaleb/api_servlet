package org.will.Utils;

import org.will.ServletApi;
import org.will.annotation.RequestMapping;
import org.will.servlets.AbstractServlet;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

public class PackageScanner {

    public static List<Class<? extends AbstractServlet>> getAbstractServletClassesInPackage() {

        Reflections reflections = new Reflections(ServletApi.class.getPackage().getName());

        List<Class<? extends AbstractServlet>> list = reflections.getSubTypesOf(AbstractServlet.class)
                .stream()
                .filter(clazz -> clazz.isAnnotationPresent(RequestMapping.class))
                .collect(Collectors.toList());

        return list;

    }
}