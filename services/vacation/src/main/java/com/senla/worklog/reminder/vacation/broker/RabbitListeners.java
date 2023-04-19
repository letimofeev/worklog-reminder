package com.senla.worklog.reminder.vacation.broker;

import com.senla.worklog.reminder.vacation.event.RegionCreatedEvent;
import com.senla.worklog.reminder.vacation.event.RegionDeletedEvent;
import com.senla.worklog.reminder.vacation.event.RegionUpdatedEvent;
import com.senla.worklog.reminder.vacation.service.RegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.senla.worklog.reminder.vacation.event.RegionEventType.REGION_CREATED;
import static com.senla.worklog.reminder.vacation.event.RegionEventType.REGION_DELETED;
import static com.senla.worklog.reminder.vacation.event.RegionEventType.REGION_UPDATED;


@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitListeners {
    private final RegionService regionService;

    @RabbitListener(queues = "${rabbitmq.region.queue.created}")
    public void handleRegionCreatedEvent(RegionCreatedEvent event) {
        log.debug("Processing {} event: {}", REGION_CREATED, event);

        var region = event.getData();
        regionService.addRegion(region);
    }

    @RabbitListener(queues = "${rabbitmq.region.queue.updated}")
    public void handleRegionUpdatedEvent(RegionUpdatedEvent event) {
        log.debug("Processing {} event: {}", REGION_UPDATED, event);

        var region = event.getData();
        regionService.updateRegion(region);
    }

    @RabbitListener(queues = "${rabbitmq.region.queue.deleted}")
    public void handleRegionDeletedEvent(RegionDeletedEvent event) {
        log.debug("Processing {} event: {}", REGION_DELETED, event);

        var eventData = event.getData();
        var regionId = eventData.getId();
        regionService.deleteRegionById(regionId);
    }
}
