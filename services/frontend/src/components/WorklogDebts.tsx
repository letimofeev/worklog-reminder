import React, {useEffect, useState} from 'react';
import '../styles/worklogDebts.scss';
import WorklogDebtsList from "./WorklogDebtsList";
import {useFetching} from "../hooks/useFetching";
import WorklogDebtsService from "../services/WorklogDebtsService";
import {EmployeeDetailsWorklogDebts} from "../models/EmployeeDetailsWorklogDebts";
import Loader from "./loader/Loader";
import CustomButton from "./button/CustomButton";

const WorklogDebts = () => {
    const [worklogDebts, setWorklogDebts] = useState<EmployeeDetailsWorklogDebts[]>([]);
    const [isSelectAllActive, setIsSelectAllActive] = useState(false);

    const [fetchDebts, isDebtsLoading, error] = useFetching(async () => {
        const response = await WorklogDebtsService.getAllDetails()
        setWorklogDebts([...worklogDebts, ...response.data])
        if (response.data.length) {
            setIsSelectAllActive(true);
        }
    })

    useEffect(() => {
        fetchDebts()
    }, [])

    return (
        <div className="worklog-debts">
            <div className="worklog-debts__container">
                <div className="worklog-debts__header">
                    Worklog Debts Management
                </div>
                <div className="worklog-debts__subheader">
                    <div className="worklog-debts__subheader__text">
                        Manage employees work logs and notifications
                    </div>
                    <div className="worklog-debts__subheader__selection">
                        <div className="worklog-debts__subheader__selection__toggle-all">
                            <CustomButton isActive={isSelectAllActive}/>
                        </div>
                    </div>
                </div>
                {isDebtsLoading ?
                    <div className="worklog-debts__loader">
                        <Loader/>
                    </div>
                    :
                    <WorklogDebtsList
                        employeesDebts={worklogDebts}
                    />
                }
            </div>
        </div>
    );
};

export default WorklogDebts;
