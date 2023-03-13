import React, {useEffect, useState} from 'react';
import '../styles/worklogDebtsList.scss'
import {EmployeeDetails} from "../models/employee/EmployeeDetails";
import {DayWorklogDebt} from "../models/worklogdebt/DayWorklogDebt";
import EmpNotificationStatus from "./EmpNotificationStatus";
import WorklogDebtsExpanded from "./WorklogDebtsExpanded";
import RoundCheckbox from "./checkbox/RoundCheckbox";
import {CSSTransition} from 'react-transition-group';
import {NotificationLoadingStatus} from "./WorklogDebts";
import Loader from "./loader/Loader";
import SuccessIcon from "./success/SuccessIcon";
import FailIcon from "./fail/FailIcon";
import {NotificationResponse} from "../models/notification/NotificationResponse";

type WorklogDebtsItemProps = {
    employeeDetails: EmployeeDetails;
    worklogDebts: DayWorklogDebt[];
    rowNumber: number;
    toggleSelected: (index: number) => void;
    isSelected: boolean;
    notificationLoadingStatus: NotificationLoadingStatus;
    notificationResponse: NotificationResponse;
}

const WorklogDebtsItem: React.FC<WorklogDebtsItemProps> = (
    {
        employeeDetails,
        worklogDebts,
        rowNumber,
        toggleSelected,
        isSelected,
        notificationLoadingStatus,
        notificationResponse
    }) => {
    const [isExpanded, setIsExpanded] = useState(false);

    useEffect(() => {
        if (notificationLoadingStatus === NotificationLoadingStatus.Pass
            || notificationLoadingStatus === NotificationLoadingStatus.Failed) {
            toggleSelected(rowNumber - 1);
        }
    }, [notificationLoadingStatus]);

    const toggleExpanded = (event: React.MouseEvent<HTMLDivElement>) => {
        if (event.target instanceof Element
            && !event.target.closest("#debts-expanded")
            && !event.target.closest("#debts-row-actions")) {
            setIsExpanded(!isExpanded);
        }
    }

    const getBackgroundColor = () => {
        const isFailed = notificationLoadingStatus === NotificationLoadingStatus.Failed;
        if (isFailed && isSelected) {
            return "#f5e3e3";
        } else if (isFailed) {
            return "#fff4f4";
        }
        return isSelected ? "#f4f6fa" : ""
    }

    return (
        <div className="worklog-debts-list__body-row" onClick={toggleExpanded}>
            <div className="worklog-debts-list__body-row__hidden"
                 style={{backgroundColor: getBackgroundColor()}}
            >
                <div className="worklog-debts-list__no__body-cell">
                    {rowNumber}
                </div>
                <div className="worklog-debts-list__employee__body-cell">
                    {employeeDetails.firstName + ' ' + employeeDetails.lastName}
                </div>
                <div className="worklog-debts-list__debts-number__body-cell">
                    {worklogDebts.length}
                </div>
                <div className="worklog-debts-list__status__body-cell">
                    <EmpNotificationStatus
                        notificationEnabled={employeeDetails.notificationEnabled}
                        botConnected={employeeDetails.botConnected}
                    />
                </div>
                <div id="debts-row-actions" className="worklog-debts-list__actions__body-cell">
                    {notificationLoadingStatus === NotificationLoadingStatus.Inactive &&
                        <RoundCheckbox handleCheckboxChange={toggleSelected}
                                       rowNumber={rowNumber}
                                       checked={isSelected}
                        />
                    }
                    {(notificationLoadingStatus === NotificationLoadingStatus.Loading) &&
                        <Loader style={
                            {
                                height: '20px',
                                width: '20px',
                                border: '5px solid #f4f6fa',
                                borderTop: '5px solid #3498db'
                            }
                        }/>
                    }
                    {(notificationLoadingStatus === NotificationLoadingStatus.Pass) &&
                        <SuccessIcon/>
                    }
                    {(notificationLoadingStatus === NotificationLoadingStatus.Failed) &&
                        <FailIcon/>
                    }
                </div>
            </div>
            {isExpanded &&
                <CSSTransition in={isExpanded}
                               timeout={500}
                               classNames="worklog-debts-list__body-row__expanded">
                    <WorklogDebtsExpanded
                        worklogDebts={worklogDebts}
                        notificationResponse={notificationResponse}
                    />
                </CSSTransition>
            }
        </div>
    );
};

export default WorklogDebtsItem;
