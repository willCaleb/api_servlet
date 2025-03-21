package org.example.Utils;

public class StringUtils {

    public static boolean isEmpty(String value) {
        return Utils.isEmpty(value) || value.trim().isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

}
