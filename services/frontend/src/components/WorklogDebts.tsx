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
    const [selectedRows, setSelectedRows] = useState<boolean[]>([]);

    const [fetchDebts, isDebtsLoading, error] = useFetching(async () => {
        const response = await WorklogDebtsService.getAllDetails()
        setWorklogDebts([...worklogDebts, ...response.data])
        if (response.data.length) {
            const rowsNum = response.data.length;
            setSelectedRows(new Array<boolean>(rowsNum).fill(false));
            setIsSelectAllActive(true);
        }
    })

    const selectAllOrToggle = () => {
        const selectedRowsArray = Object.values(selectedRows);
        const rowsNum = selectedRows.length;
        const hasTrueElement = selectedRowsArray.some((value) => value);
        const hasFalseElement = selectedRowsArray.some((value) => !value);

        let newValue: boolean = false;
        if ((hasTrueElement && hasFalseElement) || (!hasTrueElement)) {
            newValue = true;
        }
        setSelectedRows(new Array<boolean>(rowsNum).fill(newValue));
    }

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
                            <CustomButton isActive={isSelectAllActive}
                                          callback={selectAllOrToggle}/>
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
                        selectedRows={selectedRows}
                        setSelectedRows={setSelectedRows}
                    />
                }
            </div>
        </div>
    );
};

export default WorklogDebts;
