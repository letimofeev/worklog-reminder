import React, {useEffect, useState} from 'react';
import '../styles/worklogDebts.scss';
import WorklogDebtsList from "./WorklogDebtsList";
import {useFetching} from "../hooks/useFetching";
import WorklogDebtsService from "../services/WorklogDebtsService";
import {EmployeeDetailsWorklogDebts} from "../models/EmployeeDetailsWorklogDebts";
import Loader from "./loader/Loader";
import CustomButton from "./button/CustomButton";
import DebtsNotificationService from "../services/DebtsNotificationService";
import {NotificationResponse} from "../models/NotificationResponse";
import {NotificationStatus} from "../models/NotificationStatus";

export enum NotificationLoadingStatus {
    Inactive,
    Loading,
    Pass,
    Failed
}

export type NotificationLoadingRows = {
    [login: string]: NotificationLoadingStatus;
}

const WorklogDebts = () => {
    const [worklogDebts, setWorklogDebts] = useState<EmployeeDetailsWorklogDebts[]>([]);
    const [isButtonsActive, setIsButtonsActive] = useState(false);
    const [selectedRows, setSelectedRows] = useState<boolean[]>([]);
    const [notificationLoadingRows, setNotificationLoadingRows] = useState<NotificationLoadingRows>({});

    const [fetchDebts, isDebtsLoading, error] = useFetching(async () => {
        const response = await WorklogDebtsService.getAllDetails()
        setWorklogDebts([...worklogDebts, ...response.data])
        if (response.data.length) {
            const rowsNum = response.data.length;
            setSelectedRows(new Array<boolean>(rowsNum).fill(false));
            setIsButtonsActive(true);

            const newNotificationLoadingRows = {} as NotificationLoadingRows;
            response.data.forEach(empDebts => {
                const login = empDebts.employeeDetails.skypeLogin;
                newNotificationLoadingRows[login] = NotificationLoadingStatus.Inactive;
            });
            setNotificationLoadingRows(newNotificationLoadingRows);
        }
    })

    useEffect(() => {
        fetchDebts()
    }, [])

    const sendNotifications = () => {
        const selectedDebts = [] as EmployeeDetailsWorklogDebts[];
        const loadingStatuses = {...notificationLoadingRows};
        selectedRows.forEach((row, index) => {
            if (row) {
                const empDebts = worklogDebts.at(index);
                if (empDebts) {
                    const login = empDebts.employeeDetails.skypeLogin;
                    loadingStatuses[login] = NotificationLoadingStatus.Loading;
                    selectedDebts.push(empDebts);
                }
            }
        })
        if (!selectedDebts.length) {
            return;
        }
        setNotificationLoadingRows(loadingStatuses);

        DebtsNotificationService.sendNotifications(selectedDebts,
            (event) => {
                console.log('Received event', event);
                const notificationResponse = JSON.parse(event.data) as NotificationResponse;
                const empLogin = notificationResponse.login;
                const status = notificationResponse.status;
                let loadingStatus: NotificationLoadingStatus;
                if (status === NotificationStatus.Pass) {
                    loadingStatus = NotificationLoadingStatus.Pass;
                } else {
                    loadingStatus = NotificationLoadingStatus.Failed;
                }
                setNotificationLoadingRows(prevState => ({
                    ...prevState,
                    [empLogin]: loadingStatus
                }));
            }, (error) => {
                console.error('There was an error from server', error);
            }, () => {
                console.log('Connection closed by the server');
            });
    }

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
                    <div className="worklog-debts__subheader__notification">
                        <CustomButton text={"Notify"}
                                      isActive={isButtonsActive}
                                      callback={sendNotifications}
                        />
                    </div>
                    <div className="worklog-debts__subheader__selection">
                        <div className="worklog-debts__subheader__selection__toggle-all">
                            <CustomButton text={"Select all"}
                                          isActive={isButtonsActive}
                                          callback={selectAllOrToggle}
                            />
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
                        notificationLoadingRows={notificationLoadingRows}
                    />
                }
            </div>
        </div>
    );
};

export default WorklogDebts;
