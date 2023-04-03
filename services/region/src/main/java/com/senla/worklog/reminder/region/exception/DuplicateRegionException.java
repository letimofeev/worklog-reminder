package com.senla.worklog.reminder.region.exception;

public class DuplicateRegionException extends RuntimeException {
    public DuplicateRegionException(String name) {
        super("Region with name = '" + name + "' already exists");
    }
}
