import React, {useEffect, useState} from 'react';
import '../styles/content.scss';
import '../styles/worklogDebts.scss';
import WorklogDebtsList from "../components/WorklogDebtsList";
import {useRequest} from "../hooks/useRequest";
import WorklogDebtsService from "../services/WorklogDebtsService";
import {EmployeeWorklogDebts} from "../models/worklogdebt/EmployeeWorklogDebts";
import Loader from "../components/loader/Loader";
import CustomButton from "../components/button/CustomButton";
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
    const [worklogDebts, setWorklogDebts] = useState<EmployeeWorklogDebts[]>([]);
    const [isButtonsActive, setIsButtonsActive] = useState(false);
    const [selectedRows, setSelectedRows] = useState<boolean[]>([]);
    const [notificationLoadingRows, setNotificationLoadingRows] = useState<NotificationLoadingRows>({});
    const [notificationResponses, setNotificationResponses] = useState<NotificationResponses>({});

    const [fetchDebts, isDebtsLoading, error] = useRequest(async () => {
        const response = await WorklogDebtsService.getAllEmployeesDebts()
        setWorklogDebts([...worklogDebts, ...response.data])

        if (response.data && response.data.length) {
            const rowsNum = response.data.length;

            setSelectedRows(new Array<boolean>(rowsNum).fill(false));
            setIsButtonsActive(true);

            const newNotificationLoadingRows = {} as NotificationLoadingRows;
            response.data.forEach((empDebts: EmployeeWorklogDebts) => {
                const login = empDebts.skypeLogin;
                newNotificationLoadingRows[login] = NotificationLoadingStatus.Inactive;
            });
            setNotificationLoadingRows(newNotificationLoadingRows);
        }
    })

    useEffect(() => {
        fetchDebts()
    }, [])

    const sendNotifications = () => {
        const selectedDebts = [] as EmployeeWorklogDebts[];
        const loadingStatuses = {...notificationLoadingRows};

        const handleDisconnected = (index: number, login: string) => {
            interruptWithFailure(index, login, 'Employee is not connected to notification service');
        }

        const handleDisabled = (index: number, login: string) => {
            interruptWithFailure(index, login, 'Employee notifications disabled');
        }

        const handleNotPresentLogin = (index: number, login: string) => {
            interruptWithFailure(index, login, 'Employee login is not specified');
        }

        const interruptWithFailure = (index: number, login: string, message: string) => {
            selectedRows[index] = false;
            setSelectedRows([...selectedRows]);
            loadingStatuses[login] = NotificationLoadingStatus.Failed;
            setNotificationResponses(prevState => ({
                ...prevState,
                [login]: {
                    login: login,
                    status: NotificationStatus.Failed,
                    message: message
                }
            }));
        }

        selectedRows.forEach((row, index) => {
            if (!row) {
                return;
            }
            const empDebts = worklogDebts.at(index);
            if (empDebts) {
                const login = empDebts.skypeLogin;
                if (!empDebts.notificationStatus.botConnected) {
                    handleDisconnected(index, login)
                } else if (!empDebts.notificationStatus.notificationEnabled) {
                    handleDisabled(index, login);
                } else if (!WorklogDebtsService.isLoginPresent(login)) {
                    handleNotPresentLogin(index, login);
                } else {
                    loadingStatuses[login] = NotificationLoadingStatus.Loading;
                    selectedDebts.push(empDebts);
                }
            }
        })

        if (selectedDebts.length) {
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
        }

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
        <div className="content">
            <div className="content__container">
                <div className="content__header">
                    Worklog Debts Management
                </div>
                <div className="content__subheader">
                    <div className="content__subheader__text">
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
                    <div className="content__loader">
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
