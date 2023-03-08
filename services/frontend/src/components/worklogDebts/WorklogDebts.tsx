import React from 'react';
import './worklogDebts.css';
import WorklogDebtsList from "../worklogDebtsList/WorklogDebtsList";
import {EmployeeDetails} from "../../models/EmployeeDetails";
import {EmployeeDetailsWorklogDebts} from "../../models/EmployeeDetailsWorklogDebts";
import {DayWorklogDebt} from "../../models/DayWorklogDebt";

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
                    employeesDebts={[
                        {
                            employeeDetails: {
                                id: 109,
                                firstName: 'Leonid',
                                lastName: 'Timofeev',
                                notificationEnabled: true,
                                botConnected: true
                            } as EmployeeDetails,
                            worklogDebts: [
                                {
                                    date: '2024-02-02',
                                    requiredSeconds: 3600
                                },
                                {
                                    date: '2024-02-04',
                                    requiredSeconds: 3600
                                },
                                {
                                    date: '2024-02-03',
                                    requiredSeconds: 3600
                                }
                            ] as DayWorklogDebt[]
                        } as EmployeeDetailsWorklogDebts,
                        {
                            employeeDetails: {
                                id: 109,
                                firstName: 'Lol',
                                lastName: 'Memov',
                                notificationEnabled: false,
                                botConnected: true
                            } as EmployeeDetails,
                            worklogDebts: [
                                {
                                    date: '2024-02-02',
                                    requiredSeconds: 3600
                                }
                            ] as DayWorklogDebt[]
                        } as EmployeeDetailsWorklogDebts,
                        {
                            employeeDetails: {
                                id: 1092,
                                firstName: 'Kekonid',
                                lastName: 'Kekov',
                                notificationEnabled: false,
                                botConnected: false
                            } as EmployeeDetails,
                            worklogDebts: [
                                {
                                    date: '2024-02-05',
                                    requiredSeconds: 3600
                                },
                                {
                                    date: '2024-02-10',
                                    requiredSeconds: 3600
                                },
                                {
                                    date: '2024-02-03',
                                    requiredSeconds: 3600
                                }
                            ] as DayWorklogDebt[]
                        } as EmployeeDetailsWorklogDebts,
                    ]}
                />
            </div>
        </div>
    );
};

export default WorklogDebts;
