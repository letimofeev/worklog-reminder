package com.senla.worklog.reminder.region.event;

import com.senla.worklog.reminder.region.model.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import static com.senla.worklog.reminder.region.event.RegionEventType.REGION_CREATED;
import static com.senla.worklog.reminder.region.event.RegionEventType.REGION_DELETED;
import static com.senla.worklog.reminder.region.event.RegionEventType.REGION_UPDATED;
import static java.time.Instant.now;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionEvent {
    private RegionEventType eventType;
    private Object data;
    private Long timestamp;

    public RegionEvent(RegionEventType eventType, Object data) {
        this.eventType = eventType;
        this.data = data;
        this.timestamp = now().toEpochMilli();
    }

    public static RegionEvent regionCreatedEvent(Region region) {
        return new RegionEvent(REGION_CREATED, region);
    }

    public static RegionEvent regionUpdatedEvent(Region region) {
        return new RegionEvent(REGION_UPDATED, region);
    }

    public static RegionEvent regionDeletedEvent(UUID id) {
        return new RegionEvent(REGION_DELETED, new RegionDeletedEventData(id));
    }
}
