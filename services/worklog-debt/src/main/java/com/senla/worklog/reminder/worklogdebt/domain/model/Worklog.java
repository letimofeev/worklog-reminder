package com.senla.worklog.reminder.worklogdebt.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Worklog {
    private Long id;
    private LocalDate dateStarted;
    private Long timeSpentSeconds;
    private String comment;
    private String workerJiraKey;
}
