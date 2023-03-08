import React from 'react';
import '../worklogDebtsList/worklogDebtsList.scss'
import {EmployeeDetails} from "../../models/EmployeeDetails";
import {DayWorklogDebt} from "../../models/DayWorklogDebt";
import EmpNotificationStatus from "../empNotificationStatus/EmpNotificationStatus";

interface WorklogDebtsListProps {
    employeeDetails: EmployeeDetails;
    worklogDebts: DayWorklogDebt[];
    index: number;
}

const WorklogDebtItem: React.FC<WorklogDebtsListProps> = ({employeeDetails, worklogDebts, index}) => {
    return (
        <div className="worklog-debt-list__body-row">
            <div className="worklog-debt-list__no__body-cell">{
                index
            }</div>
            <div className="worklog-debt-list__employee__body-cell">{
                employeeDetails.firstName + ' ' + employeeDetails.lastName
            }</div>
            <div className="worklog-debt-list__debts-number__body-cell">{
                worklogDebts.length
            }</div>
            <div className="worklog-debt-list__status__body-cell">
                <EmpNotificationStatus
                    notificationEnabled={employeeDetails.notificationEnabled}
                    botConnected={employeeDetails.botConnected}
                />
            </div>
            <div className="worklog-debt-list__actions__body-cell">
                Actions
            </div>
        </div>
    );
};

export default WorklogDebtItem;