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

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqListener {
    private final RegionServicePort regionServicePort;
    private final RegionEventDataMapper eventDataMapper;

    @RabbitListener(queues = "${rabbitmq.region.queue.created}")
    public void handleRegionCreatedEvent(RegionCreatedEvent event) {
        log.debug("Received event from queue for region created events");

        var eventData = event.getData();
        var region = eventDataMapper.mapToDomain(eventData);
        regionServicePort.addRegion(region);
    }

    @RabbitListener(queues = "${rabbitmq.region.queue.updated}")
    public void handleRegionUpdatedEvent(RegionUpdatedEvent event) {
        log.debug("Received event from queue for region updated events");

        var eventData = event.getData();
        var region = eventDataMapper.mapToDomain(eventData);
        regionServicePort.updateRegion(region);
    }

    @RabbitListener(queues = "${rabbitmq.region.queue.deleted}")
    public void handleRegionDeletedEvent(RegionDeletedEvent event) {
        log.debug("Received event from queue for region deleted events");

        var eventData = event.getData();
        var regionId = eventData.getId();
        regionServicePort.deleteRegionById(regionId);
    }
}
