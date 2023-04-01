package com.senla.worklog.reminder.region.broker;

import com.senla.worklog.reminder.region.event.RegionEvent;

public interface RegionBroker {
    void sendRegionEvent(RegionEvent event);
}
