package com.senla.worklog.reminder.region.exception;

import java.util.UUID;

public class RegionNotFoundException extends RuntimeException {
    public RegionNotFoundException(UUID id) {
        super("Region with id = '" + id + "' not found");
    }
}
