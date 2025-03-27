package org.will.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    public static Logger getLogger(Class aClass) {
        return LoggerFactory.getLogger(aClass);
    }

    public static Logger getLogger(String nameClass) {
        return LoggerFactory.getLogger(nameClass);
    }
}
