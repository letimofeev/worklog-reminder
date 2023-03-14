import React, {useEffect, useState} from 'react';
import '../styles/worklogDebts.scss';
import WorklogDebtsList from "./WorklogDebtsList";
import {useFetching} from "../hooks/useFetching";
import WorklogDebtsService from "../services/WorklogDebtsService";
import {EmployeeDetailsWorklogDebts} from "../models/worklogdebt/EmployeeDetailsWorklogDebts";
import Loader from "./loader/Loader";
import CustomButton from "./button/CustomButton";
import DebtsNotificationService from "../services/DebtsNotificationService";
import {NotificationResponse} from "../models/notification/NotificationResponse";
import {NotificationStatus} from "../models/notification/NotificationStatus";

export enum NotificationLoadingStatus {
    Inactive,
    Loading,
    Pass,
    Failed
}

export type NotificationLoadingRows = {
    [login: string]: NotificationLoadingStatus;
}

export type NotificationResponses = {
    [login: string]: NotificationResponse;
}

const WorklogDebts = () => {
    const [worklogDebts, setWorklogDebts] = useState<EmployeeDetailsWorklogDebts[]>([]);
    const [isButtonsActive, setIsButtonsActive] = useState(false);
    const [selectedRows, setSelectedRows] = useState<boolean[]>([]);
    const [notificationLoadingRows, setNotificationLoadingRows] = useState<NotificationLoadingRows>({});
    const [notificationResponses, setNotificationResponses] = useState<NotificationResponses>({});

    const [fetchDebts, isDebtsLoading, error] = useFetching(async () => {
        const response = await WorklogDebtsService.getAllDetails()
        setWorklogDebts([...worklogDebts, ...response.data])

        if (response.data && response.data.length) {
            const rowsNum = response.data.length;

            setSelectedRows(new Array<boolean>(rowsNum).fill(false));
            setIsButtonsActive(true);

            const newNotificationLoadingRows = {} as NotificationLoadingRows;
            response.data.forEach((empDebts: EmployeeDetailsWorklogDebts) => {
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

        const handleNullLogin = (index: number, login: string) => {
            selectedRows[index] = false;
            setSelectedRows([...selectedRows]);
            loadingStatuses[login] = NotificationLoadingStatus.Failed;
            setNotificationResponses(prevState => ({
                ...prevState,
                [login]: {
                    login: login,
                    status: NotificationStatus.Failed,
                    message: 'Login is not specified'
                }
            }))
        }

        selectedRows.forEach((row, index) => {
            if (!row) {
                return;
            }
            const empDebts = worklogDebts.at(index);
            if (empDebts) {
                const login = empDebts.employeeDetails.skypeLogin;
                if (login !== null) {
                    loadingStatuses[login] = NotificationLoadingStatus.Loading;
                    selectedDebts.push(empDebts);
                } else {
                    handleNullLogin(index, login);
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

                if (status === NotificationStatus.Pass) {
                    loadingStatuses[empLogin] = NotificationLoadingStatus.Pass;
                } else {
                    loadingStatuses[empLogin] = NotificationLoadingStatus.Failed;
                }
                setNotificationResponses(prevState => ({
                    ...prevState,
                    [empLogin]: notificationResponse
                }))
            }, (error) => {
                console.error('There was an error from server', error);
            }, () => {
                console.log('Connection closed by the server');
            });
        setNotificationLoadingRows(loadingStatuses);
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
                        notificationResponses={notificationResponses}
                    />
                }
            </div>
        </div>
    );
};

export default WorklogDebts;
