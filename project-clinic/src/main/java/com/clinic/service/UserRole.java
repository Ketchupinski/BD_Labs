package com.clinic.service;

public enum UserRole {
    PATIENT(1),
    DOCTOR(2);

    private final int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserRole getRole(int value) {
        for (UserRole role : UserRole.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        return null;
    }

    public static UserRole getRole(String value) {
        for (UserRole role : UserRole.values()) {
            if (role.name().equals(value)) {
                return role;
            }
        }
        return null;
    }
}
