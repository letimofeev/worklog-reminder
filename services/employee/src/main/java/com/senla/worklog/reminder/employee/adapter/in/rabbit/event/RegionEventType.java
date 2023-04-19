package com.senla.worklog.reminder.employee.adapter.in.rabbit.event;

public enum RegionEventType {
    REGION_CREATED,
    REGION_UPDATED,
    REGION_DELETED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
