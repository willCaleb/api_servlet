package org.will.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

public class LogConfig {

    public static void configure() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        context.getLogger(Logger.ROOT_LOGGER_NAME).setLevel(Level.INFO);

        context.getLogger("org.apache").setLevel(Level.WARN);
        context.getLogger("org.eclipse.jetty").setLevel(Level.WARN);
        context.getLogger("org.hibernate").setLevel(Level.WARN);

        context.getLogger("org.will").setLevel(Level.INFO);
    }
}