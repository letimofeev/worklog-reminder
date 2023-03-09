import React from 'react';

interface WorklogDebtItemProps {
    date: string;
    requiredSeconds: number;
    index: number;
}

const WorklogDebtItem: React.FC<WorklogDebtItemProps> = ({date, requiredSeconds, index}) => {
    const formatTime = (seconds: number): string => {
        const hours = seconds / 3600;
        const minutes = (seconds % 3600) / 60;
        const remainingSeconds = seconds % 60;
        return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${remainingSeconds.toString().padStart(2, '0')}`;
    }
    return (
        <div className="worklog-debts-list__body-row__expanded__debt-row">
            <div className="worklog-debts-list__body-row__expanded__debt-row__no__body-cell">
                {index}
            </div>
            <div className="worklog-debts-list__body-row__expanded__debt-row__date__body-cell">
                {date}
            </div>
            <div className="worklog-debts-list__body-row__expanded__debt-row__time__body-cell">
                {formatTime(requiredSeconds)}
            </div>
        </div>
    );
};

export default WorklogDebtItem;