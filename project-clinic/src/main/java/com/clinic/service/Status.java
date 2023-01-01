package com.clinic.service;

public enum Status {
    PENDING(1, "Pending"),
    DONE(2, "Done"),
    CANCELED(3, "Canceled"),
    IN_PROGRESS(4, "In progress");

    private final int id;
    private final String name;

    Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Status getById(int id) {
        for (Status status : Status.values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        return null;
    }

    public static Status getByName(String name) {
        for (Status status : Status.values()) {
            if (status.getName().equals(name)) {
                return status;
            }
        }
        return null;
    }
}
