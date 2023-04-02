package com.senla.worklog.reminder.employee.adapter.in.rabbit.event.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionEventData {
    private UUID id;
    private String name;
}
