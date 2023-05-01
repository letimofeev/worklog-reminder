import React from 'react';
import '../../../styles/calendarVacationList.scss'
import {CalendarVacation} from "../../../models/vacation/CalendarVacation";

type CalendarVacationItemProps = {
    rowNumber: number;
    vacation: CalendarVacation;
}

const CalendarVacationItem: React.FC<CalendarVacationItemProps> = ({rowNumber, vacation}) => {
    return (
        <div className="calendar-vacation-list__body-row">
            <div className="calendar-vacation-list__no__body-cell">{rowNumber}</div>
            <div className="calendar-vacation-list__date__body-cell">{vacation.date}</div>
            <div className="calendar-vacation-list__description__body-cell">{vacation.name}</div>
            <div className="calendar-vacation-list__actions__body-cell">Actions</div>
        </div>
    );
};

export default CalendarVacationItem;
