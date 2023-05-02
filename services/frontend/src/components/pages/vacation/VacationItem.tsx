import React from 'react';
import '../../../styles/vacationList.scss'
import {CalendarVacation} from "../../../models/vacation/CalendarVacation";
import {EmployeeVacation} from "../../../models/vacation/EmployeeVacation";

type CalendarVacationItemProps = {
    rowNumber: number;
    vacation: CalendarVacation | EmployeeVacation;
}

const VacationItem: React.FC<CalendarVacationItemProps> = ({rowNumber, vacation}) => {
    return (
        <div className="vacation-list__body-row">
            <div className="vacation-list__no__body-cell">{rowNumber}</div>
            <div className="vacation-list__date__body-cell">{vacation.date}</div>
            <div className="vacation-list__description__body-cell">{vacation.name}</div>
        </div>
    );
};

export default VacationItem;
