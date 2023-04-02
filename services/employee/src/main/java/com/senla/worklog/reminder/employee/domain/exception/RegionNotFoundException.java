package com.senla.worklog.reminder.employee.domain.exception;

import java.util.UUID;

public class RegionNotFoundException extends DomainNotFoundException {
    public RegionNotFoundException(UUID regionId) {
        super("Region with id = '" + regionId + "' not found", "id", String.valueOf(regionId));
    }

    public RegionNotFoundException(String message, String attributeName, String attributeValue) {
        super(message, attributeName, attributeValue);
    }
}
