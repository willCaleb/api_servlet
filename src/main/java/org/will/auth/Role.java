package org.will.auth;

import jakarta.persistence.Converter;

public enum Role {

    ROOT(1), ADMIN(2), USER(3);

    private final int level;

    Role(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
    public boolean hasPermission(Role requiredRole) {
        return this.level <= requiredRole.getLevel();
    }

}
