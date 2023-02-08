package com.senla.worklog.reminder.validator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, Extended.class})
public interface ValidationSequence {
}
