import React from 'react';

interface VacationItemProps {
    date: string;
    description: string;
    index: number;
}

const WorklogDebtsVacationItem: React.FC<VacationItemProps> = (
    {
        date,
        description,
        index
    }) => {
    return (
        <div className="worklog-debts-list__body-row__expanded__debt-row">
            <div className="worklog-debts-list__body-row__expanded__debt-row__vacation-no__body-cell">
                {index}
            </div>
            <div className="worklog-debts-list__body-row__expanded__debt-row__vacation-date__body-cell">
                {date}
            </div>
            <div className="worklog-debts-list__body-row__expanded__debt-row__vacation-description__body-cell">
                {description}
            </div>
        </div>
    );
};

export default WorklogDebtsVacationItem;