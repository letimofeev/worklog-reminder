import React, {useState} from 'react';
import '../styles/worklogDebtsList.scss'
import {EmployeeDetails} from "../models/EmployeeDetails";
import {DayWorklogDebt} from "../models/DayWorklogDebt";
import EmpNotificationStatus from "./EmpNotificationStatus";
import WorklogDebtsExpanded from "./WorklogDebtsExpanded";

interface WorklogDebtsListProps {
    employeeDetails: EmployeeDetails;
    worklogDebts: DayWorklogDebt[];
    index: number;
}

const WorklogDebtsItem: React.FC<WorklogDebtsListProps> = ({employeeDetails, worklogDebts, index}) => {
    const [isExpanded, setIsExpanded] = useState(false);

    const toggleExpanded = (event: React.MouseEvent<HTMLDivElement>) => {
        if (event.target instanceof Element
            && (!event.target.closest("#debts-expanded") || event.target.closest("#debts-expanded-hider"))) {
            setIsExpanded(!isExpanded);
        }
    }

    return (
        <div className="worklog-debts-list__body-row" onClick={toggleExpanded}>
            <div className="worklog-debts-list__body-row__hidden">
                <div className="worklog-debts-list__no__body-cell">
                    {index}
                </div>
                <div className="worklog-debts-list__employee__body-cell">
                    {employeeDetails.firstName + ' ' + employeeDetails.lastName}
                </div>
                <div className="worklog-debts-list__debts-number__body-cell">
                    {worklogDebts.length}
                </div>
                <div className="worklog-debts-list__status__body-cell">
                    <EmpNotificationStatus
                        notificationEnabled={employeeDetails.notificationEnabled}
                        botConnected={employeeDetails.botConnected}
                    />
                </div>
                <div className="worklog-debts-list__actions__body-cell">
                    Actions
                </div>
            </div>
            {isExpanded &&
                <WorklogDebtsExpanded worklogDebts={worklogDebts}/>
            }
        </div>
    );
};

export default WorklogDebtsItem;