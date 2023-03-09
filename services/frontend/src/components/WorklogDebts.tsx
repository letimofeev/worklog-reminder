import React, {useEffect, useState} from 'react';
import '../styles/worklogDebts.scss';
import WorklogDebtsList from "./WorklogDebtsList";
import {useFetching} from "../hooks/useFetching";
import WorklogDebtsService from "../services/WorklogDebtsService";
import {EmployeeDetailsWorklogDebts} from "../models/EmployeeDetailsWorklogDebts";

const WorklogDebts = () => {
    const [worklogDebts, setWorklogDebts] = useState<EmployeeDetailsWorklogDebts[]>([]);

    const [fetchDebts, isDebtsLoading, error] = useFetching(async () => {
        const response = await WorklogDebtsService.getAllDetails()
        setWorklogDebts([...worklogDebts, ...response.data])
    })

    useEffect(() => {
        fetchDebts()
    }, [])

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
                    employeesDebts={worklogDebts}
                />
            </div>
        </div>
    );
};

export default WorklogDebts;
