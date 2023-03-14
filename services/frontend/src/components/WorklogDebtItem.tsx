import React from 'react';

interface WorklogDebtItemProps {
    date: string;
    timeDeptSeconds: number;
    index: number;
}

const WorklogDebtItem: React.FC<WorklogDebtItemProps> = (
    {
        date,
        timeDeptSeconds,
        index
    }) => {
    const formatTime = (seconds: number): string => {
        const hours = Math.floor(seconds / 3600);
        const minutes = Math.floor((seconds % 3600) / 60);
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
                {formatTime(timeDeptSeconds)}
            </div>
        </div>
    );
};

export default WorklogDebtItem;