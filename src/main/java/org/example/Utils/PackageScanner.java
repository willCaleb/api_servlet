package org.example.Utils;

import org.example.servlets.AbstractServlet;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.List;

public class PackageScanner {

    public static List<Class<? extends AbstractServlet>> getClassesInPackage(String pack) {

        Reflections reflections = new Reflections(pack);

        List<Class<? extends AbstractServlet>> list = reflections.getSubTypesOf(AbstractServlet.class)
                .stream()
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .toList();

        return list;

    }
}