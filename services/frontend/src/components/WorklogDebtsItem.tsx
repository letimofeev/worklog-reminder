import React from 'react';
import '../styles/worklogDebtsList.scss'
import {EmployeeDetails} from "../models/EmployeeDetails";
import {DayWorklogDebt} from "../models/DayWorklogDebt";
import EmpNotificationStatus from "./EmpNotificationStatus";

interface WorklogDebtsListProps {
    employeeDetails: EmployeeDetails;
    worklogDebts: DayWorklogDebt[];
    index: number;
}

const WorklogDebtsItem: React.FC<WorklogDebtsListProps> = ({employeeDetails, worklogDebts, index}) => {
    return (
        <div className="worklog-debts-list__body-row">
            <div className="worklog-debts-list__no__body-cell">{
                index
            }</div>
            <div className="worklog-debts-list__employee__body-cell">{
                employeeDetails.firstName + ' ' + employeeDetails.lastName
            }</div>
            <div className="worklog-debts-list__debts-number__body-cell">{
                worklogDebts.length
            }</div>
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
    );
};

export default WorklogDebtsItem;