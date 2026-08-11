package org.will.context;

import org.will.model.entity.User;

public class Context {

    private static final ThreadLocal<User> userThread = new ThreadLocal<>();

    public static void setUser(User user) {
        userThread.set(user);
    }

    public static User getUser() {
        return userThread.get();
    }

    public static void clear() {
        userThread.remove();
    }

}
