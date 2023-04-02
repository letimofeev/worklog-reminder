package com.senla.worklog.reminder.employee.adapter.in.rabbit.event;

import com.senla.worklog.reminder.employee.adapter.in.rabbit.event.data.RegionEventData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionCreatedEvent {
    private RegionEventType eventType;
    private RegionEventData data;

    @Override
    public String toString() {
        return "RegionCreatedEvent{" +
                "eventType=" + eventType +
                ", data=" + data +
                '}';
    }
}
