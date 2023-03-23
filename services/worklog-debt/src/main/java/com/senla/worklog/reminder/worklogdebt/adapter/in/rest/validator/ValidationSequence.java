package com.senla.worklog.reminder.worklogdebt.adapter.in.rest.validator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, ExtendedGroup.class})
public interface ValidationSequence {
}
