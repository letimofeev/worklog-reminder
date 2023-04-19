package com.senla.worklog.reminder.vacation.event;

import com.senla.worklog.reminder.vacation.model.Region;
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
    private Region data;
    private Long timestamp;

    @Override
    public String toString() {
        return "RegionCreatedEvent{" +
                "eventType=" + eventType +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}
