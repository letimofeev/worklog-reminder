package com.senla.worklog.reminder.employee.adapter.in.rabbit;

import com.senla.worklog.reminder.employee.adapter.in.rabbit.event.RegionCreatedEvent;
import com.senla.worklog.reminder.employee.adapter.in.rabbit.event.RegionDeletedEvent;
import com.senla.worklog.reminder.employee.adapter.in.rabbit.event.RegionUpdatedEvent;
import com.senla.worklog.reminder.employee.adapter.in.rabbit.mapper.RegionEventDataMapper;
import com.senla.worklog.reminder.employee.domain.port.in.RegionServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.senla.worklog.reminder.employee.adapter.in.rabbit.event.RegionEventType.REGION_CREATED;
import static com.senla.worklog.reminder.employee.adapter.in.rabbit.event.RegionEventType.REGION_DELETED;
import static com.senla.worklog.reminder.employee.adapter.in.rabbit.event.RegionEventType.REGION_UPDATED;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqListener {
    private final RegionServicePort regionServicePort;
    private final RegionEventDataMapper eventDataMapper;

    @RabbitListener(queues = "${rabbitmq.region.queue.created}")
    public void handleRegionCreatedEvent(RegionCreatedEvent event) {
        log.debug("Processing event from queue for {} events: {}", REGION_CREATED, event);

        var eventData = event.getData();
        var region = eventDataMapper.mapToDomain(eventData);
        regionServicePort.addRegion(region);
    }

    @RabbitListener(queues = "${rabbitmq.region.queue.updated}")
    public void handleRegionUpdatedEvent(RegionUpdatedEvent event) {
        log.debug("Processing event from queue for {} events: {}", REGION_UPDATED, event);

        var eventData = event.getData();
        var region = eventDataMapper.mapToDomain(eventData);
        regionServicePort.updateRegion(region);
    }

    @RabbitListener(queues = "${rabbitmq.region.queue.deleted}")
    public void handleRegionDeletedEvent(RegionDeletedEvent event) {
        log.debug("Processing event from queue for {} events: {}", REGION_DELETED, event);

        var eventData = event.getData();
        var regionId = eventData.getId();
        regionServicePort.deleteRegionById(regionId);
    }
}
