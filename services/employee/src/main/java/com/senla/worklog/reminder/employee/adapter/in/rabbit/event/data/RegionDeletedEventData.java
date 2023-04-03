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
public class RegionDeletedEventData {
    private UUID id;

    @Override
    public String toString() {
        return "RegionDeletedEventData{" +
                "id=" + id +
                '}';
    }
}
