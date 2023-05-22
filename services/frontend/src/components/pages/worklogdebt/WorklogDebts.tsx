import React, {useEffect, useState} from 'react';
import '../../../styles/content.scss';
import '../../../styles/worklogDebts.scss';
import WorklogDebtsList from "./WorklogDebtsList";
import {useRequest} from "../../../hooks/useRequest";
import WorklogDebtsService from "../../../api/WorklogDebtsService";
import {EmployeeWorklogDebts} from "../../../models/worklogdebt/EmployeeWorklogDebts";
import Loader from "../../loader/Loader";
import CustomButton from "../../button/CustomButton";
import DebtsNotificationService from "../../../api/DebtsNotificationService";
import {NotificationResponse} from "../../../models/notification/NotificationResponse";
import {NotificationStatus} from "../../../models/notification/NotificationStatus";
import {addDays} from "date-fns";
import {DateRangePicker} from "react-date-range";

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
    const [isDateRangePickerCollapsed, setIsDateRangePickerCollapsed] = useState(true);
    const [dateRange, setDateRange] = useState(
        {
            startDate: new Date(),
            endDate: addDays(new Date(), 7),
            key: 'selection',
        },
    );

    const handleDateRangeClick = (event: React.MouseEvent<HTMLDivElement | HTMLButtonElement>) => {
        event.stopPropagation();
        if (!isDateRangePickerCollapsed) {
            handleDateRangeClose(event);
        } else {
            setIsDateRangePickerCollapsed(false);
        }
    };

    const handleDateRangeClose = (event: React.MouseEvent<HTMLDivElement | HTMLButtonElement>) => {
        event.stopPropagation();
        if (!isDateRangePickerCollapsed) {
            const dateFrom = convertToISO(dateRange.startDate);
            const dateTo = convertToISO(dateRange.endDate);
            fetchDebts(dateFrom, dateTo);
            setIsDateRangePickerCollapsed(true);
        }
    };

    const convertToISO = (date: Date) => {
        return date.toLocaleDateString().split('.').reverse().join('-')
    }

    const [fetchDebts, isDebtsLoading, error] = useRequest(async (dateFrom: string, dateTo: string) => {
        const debts = (dateFrom && dateTo) ?
            await WorklogDebtsService.getEmployeesDebts(dateFrom, dateTo) :
            await WorklogDebtsService.getCurrentWeekEmployeesDebts();
        setWorklogDebts([...debts])

        if (debts && debts.length) {
            setSelectedRows(new Array<boolean>(debts.length).fill(false));
            setIsButtonsActive(true);

            const newNotificationLoadingRows = {} as NotificationLoadingRows;
            debts.forEach((empDebts: EmployeeWorklogDebts) => {
                const login = empDebts.skypeLogin;
                newNotificationLoadingRows[login] = NotificationLoadingStatus.Inactive;
            });
            setNotificationLoadingRows(newNotificationLoadingRows);
        } else {
            setIsButtonsActive(false);
        }
    })

    useEffect(() => {
        fetchDebts();
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
        <div className="content" onClick={handleDateRangeClose}>
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
                <div className="ml-4">
                    <button
                        onClick={(e) => handleDateRangeClick(e)}
                        className={`py-2 px-4 text-white font-semibold rounded ${
                            isDateRangePickerCollapsed ? 'bg-blue-500 hover:bg-blue-700' : 'bg-blue-700 hover:bg-blue-800'
                        }`}
                    >
                        Choose dates
                    </button>
                    <div className="collapsable-container">
                        {!isDateRangePickerCollapsed &&
                            <div
                                onClick={e => e.stopPropagation()}
                                className="collapsable-content absolute bg-white z-10 mt-1 p-4 border border-gray-300 rounded">
                                <DateRangePicker
                                    onChange={(item: any) => setDateRange(item.selection)}
                                    showPreview={true}
                                    moveRangeOnFirstSelection={false}
                                    months={1}
                                    ranges={[dateRange]}
                                    direction="horizontal"
                                />
                            </div>
                        }
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
