package com.senla.worklog.reminder.worklogdebtnotification.validator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, ExtendedGroup.class})
public interface ValidationSequence {
}
