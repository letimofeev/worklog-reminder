import React from 'react';
import WorklogDebtItem from "./WorklogDebtItem";
import {DayWorklogDebt} from "../models/DayWorklogDebt";

interface WorklogDebtsExpandedProps {
    worklogDebts: DayWorklogDebt[];
}

const WorklogDebtsExpanded: React.FC<WorklogDebtsExpandedProps> = ({worklogDebts}) => {
    return (
        <div id="debts-expanded" className="worklog-debts-list__body-row__expanded">
            <div className="worklog-debts-list__body-row__expanded__container">
                <div className="worklog-debts-list__body-row__expanded__header">
                    <div className="worklog-debts-list__body-row__expanded__debt-row__no__header-cell">
                        No
                    </div>
                    <div className="worklog-debts-list__body-row__expanded__debt-row__date__header-cell">
                        Date
                    </div>
                    <div className="worklog-debts-list__body-row__expanded__debt-row__time__header-cell">
                        Time
                    </div>
                </div>
                <div className="worklog-debts-list__body-row__expanded__body__container">
                    {worklogDebts.map((worklogDebt, index) => (
                        <WorklogDebtItem
                            key={index}
                            date={worklogDebt.date}
                            requiredSeconds={worklogDebt.requiredSeconds}
                            index={index + 1}
                        />
                    ))}
                </div>
                <div id="debts-expanded-hider" className="worklog-debts-list__body-row__expanded__hider"/>
            </div>
        </div>
    );
};

export default WorklogDebtsExpanded;