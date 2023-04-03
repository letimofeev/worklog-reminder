package com.senla.worklog.reminder.employee.adapter.in.rabbit.event;

import com.senla.worklog.reminder.employee.adapter.in.rabbit.event.data.RegionDeletedEventData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionDeletedEvent {
    private RegionEventType eventType;
    private RegionDeletedEventData data;
    private Long timestamp;

    @Override
    public String toString() {
        return "RegionDeletedEvent{" +
                "eventType=" + eventType +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}
