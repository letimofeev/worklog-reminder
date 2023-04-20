import React, {useEffect, useState} from 'react';
import '../styles/worklogDebtsList.scss'
import EmpNotificationStatus from "./EmpNotificationStatus";
import WorklogDebtsExpanded from "./WorklogDebtsExpanded";
import RoundCheckbox from "./checkbox/RoundCheckbox";
import {CSSTransition} from 'react-transition-group';
import {NotificationLoadingStatus} from "./WorklogDebts";
import Loader from "./loader/Loader";
import SuccessIcon from "./status/SuccessIcon";
import FailIcon from "./status/FailIcon";
import {NotificationResponse} from "../models/notification/NotificationResponse";
import {BsFillQuestionCircleFill} from "react-icons/bs";
import {EmployeeWorklogDebts} from "../models/worklogdebt/EmployeeWorklogDebts";

type WorklogDebtsItemProps = {
    employeeDebts: EmployeeWorklogDebts;
    rowNumber: number;
    toggleSelected: (index: number) => void;
    setIsSelected: (index: number, value: boolean) => void;
    isSelected: boolean;
    notificationLoadingStatus: NotificationLoadingStatus;
    notificationResponse: NotificationResponse;
}

const WorklogDebtsItem: React.FC<WorklogDebtsItemProps> = (
    {
        employeeDebts,
        rowNumber,
        toggleSelected,
        setIsSelected,
        isSelected,
        notificationLoadingStatus,
        notificationResponse
    }) => {
    const [isExpanded, setIsExpanded] = useState(false);
    const [wasExpandedAfterNotificationFail, setWasExpandedAfterNotificationFail] = useState(false);
    const [backgroundColor, setBackgroundColor] = useState("");

    useEffect(() => {
        const isPass = notificationLoadingStatus === NotificationLoadingStatus.Pass;
        const isFailed = notificationLoadingStatus === NotificationLoadingStatus.Failed;
        if (isPass || isFailed) {
            setIsSelected(rowNumber - 1, false);
        }
        setWasExpandedAfterNotificationFail(false);
    }, [notificationLoadingStatus]);

    useEffect(() => {
        const isFailed = notificationLoadingStatus === NotificationLoadingStatus.Failed;
        const color = getBackgroundColor(wasExpandedAfterNotificationFail, isFailed, isSelected);
        setBackgroundColor(color);
    }, [isExpanded, isSelected, notificationLoadingStatus]);

    const getBackgroundColor = (
        wasExpandedAfterNotificationFail: boolean,
        isFailed: boolean,
        isSelected: boolean
    ): string => {
        if (!wasExpandedAfterNotificationFail) {
            if (isFailed && isSelected) {
                return "#f5e3e3";
            } else if (isFailed) {
                return "#fff4f4";
            }
            return isSelected ? "#f4f6fa" : ""
        }
        return isSelected ? "#f4f6fa" : ""
    }

    const toggleExpanded = (event: React.MouseEvent<HTMLDivElement>) => {
        if (event.target instanceof Element
            && !event.target.closest("#debts-expanded")
            && (!event.target.closest("#debts-row-actions")
                || event.target.closest("#notification-failed-status-icon"))) {
            if (!isExpanded && notificationLoadingStatus === NotificationLoadingStatus.Failed) {
                setWasExpandedAfterNotificationFail(true);
            }
            setIsExpanded(!isExpanded);
        }
    }

    return (
        <div className="worklog-debts-list__body-row" onClick={toggleExpanded}>
            <div className="worklog-debts-list__body-row__collapsed"
                 style={{backgroundColor: backgroundColor}}
            >
                <div className="worklog-debts-list__no__body-cell">
                    {rowNumber}
                </div>
                <div className="worklog-debts-list__employee__body-cell">
                    {employeeDebts.firstName + ' ' + employeeDebts.lastName}
                </div>
                <div className="worklog-debts-list__debts-number__body-cell">
                    {employeeDebts.worklogDebts.length}
                </div>
                <div className="worklog-debts-list__status__body-cell">
                    <EmpNotificationStatus
                        notificationEnabled={employeeDebts.notificationStatus.notificationEnabled}
                        botConnected={employeeDebts.notificationStatus.botConnected}
                    />
                </div>
                <div id="debts-row-actions" className="worklog-debts-list__actions__body-cell">
                    <div className="worklog-debts-list__actions__body-cell__status-icon">
                        {(notificationLoadingStatus === NotificationLoadingStatus.Loading) &&
                            <Loader style={
                                {
                                    height: '20px',
                                    width: '20px',
                                    border: '5px solid #f4f6fa',
                                    borderTop: '5px solid #3498db',
                                    display: 'inline-block',
                                    marginTop: '7px',
                                    marginLeft: '30px'
                                }
                            }/>
                        }
                        {(notificationLoadingStatus === NotificationLoadingStatus.Pass) &&
                            <div className="worklog-debts-list__actions__body-cell__status-icon__pass">
                                <SuccessIcon style={
                                    {
                                        marginTop: '7px',
                                        marginLeft: '30px'
                                    }
                                }/>
                            </div>
                        }
                        {(notificationLoadingStatus === NotificationLoadingStatus.Failed) &&
                            <div id="notification-failed-status-icon"
                                 className="worklog-debts-list__actions__body-cell__status-icon__failed">
                                {!wasExpandedAfterNotificationFail &&
                                    <BsFillQuestionCircleFill style={
                                        {
                                            marginRight: '10px',
                                            width: '20px',
                                            height: '20px',
                                            color: '#a4bade'
                                        }
                                    }/>
                                }
                                {<FailIcon style={
                                    {
                                        marginRight: !wasExpandedAfterNotificationFail ? 'auto' : undefined,
                                        cursor: !wasExpandedAfterNotificationFail ? 'pointer' : undefined,
                                        marginTop: '7px',
                                        marginLeft: wasExpandedAfterNotificationFail ? '30px' : undefined,
                                    }
                                }/>
                                }
                            </div>
                        }
                    </div>
                    <RoundCheckbox handleCheckboxChange={toggleSelected}
                                   rowNumber={rowNumber}
                                   checked={isSelected}
                                   style={
                                       {
                                           marginLeft: 'auto',
                                           marginRight: '30%'
                                       }
                                   }
                    />
                </div>
            </div>
            {isExpanded &&
                <CSSTransition in={isExpanded}
                               timeout={500}
                               classNames="worklog-debts-list__body-row__expanded">
                    <WorklogDebtsExpanded
                        worklogDebts={employeeDebts.worklogDebts}
                        notificationResponse={notificationResponse}
                    />
                </CSSTransition>
            }
        </div>
    );
};

export default WorklogDebtsItem;
