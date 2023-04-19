package com.senla.worklog.reminder.vacation.event;

public enum RegionEventType {
    REGION_CREATED,
    REGION_UPDATED,
    REGION_DELETED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
