package org.will.Utils;

import org.will.servlets.AbstractServlet;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.List;

public class PackageScanner {

    public static List<Class<? extends AbstractServlet>> getAbstractServletClassesInPackage(String pack) {

        Reflections reflections = new Reflections(pack);

        List<Class<? extends AbstractServlet>> list = reflections.getSubTypesOf(AbstractServlet.class)
                .stream()
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .toList();

        return list;

    }
}