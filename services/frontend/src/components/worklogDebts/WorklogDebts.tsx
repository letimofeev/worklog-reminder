import React from 'react';
import './worklogDebts.css';
import WorklogDebtsList from "../worklogDebtsList/WorklogDebtsList";

const WorklogDebts = () => {
    return (
        <div className="worklog-debts__container">
            <div className="worklog-debts">
                <div className="worklog-debts__header">
                    Worklog Debts Management
                </div>
                <div className="worklog-debts__subheader">
                    Manage employees work logs and notifications
                </div>
                <WorklogDebtsList
                    employeesDebts={[]}
                />
            </div>
        </div>
    );
};

export default WorklogDebts;
