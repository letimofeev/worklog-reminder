import React from 'react';

interface WorklogDebtItemProps {
    date: string;
    requiredSeconds: number;
    index: number;
}

const WorklogDebtItem: React.FC<WorklogDebtItemProps> = ({date, requiredSeconds, index}) => {
    return (
        <div className="worklog-debts-list__body-row__expanded__debt-row">
            <div className="worklog-debts-list__body-row__expanded__debt-row__no">
                {index}
            </div>
            <div className="worklog-debts-list__body-row__expanded__debt-row__date">
                {date}
            </div>
            <div className="worklog-debts-list__body-row__expanded__debt-row__time">
                {requiredSeconds}
            </div>
        </div>
    );
};

export default WorklogDebtItem;