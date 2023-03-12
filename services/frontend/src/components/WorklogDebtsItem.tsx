import React, {useState} from 'react';
import '../styles/worklogDebtsList.scss'
import {EmployeeDetails} from "../models/EmployeeDetails";
import {DayWorklogDebt} from "../models/DayWorklogDebt";
import EmpNotificationStatus from "./EmpNotificationStatus";
import WorklogDebtsExpanded from "./WorklogDebtsExpanded";
import RoundCheckbox from "./checkbox/RoundCheckbox";
import {CSSTransition} from 'react-transition-group';
import {NotificationLoadingStatus} from "./WorklogDebts";

type WorklogDebtsListProps = {
    employeeDetails: EmployeeDetails;
    worklogDebts: DayWorklogDebt[];
    rowNumber: number;
    handleCheckboxChange: (index: number) => void;
    isSelected: any;
    notificationLoadingStatus: NotificationLoadingStatus;
}

const WorklogDebtsItem: React.FC<WorklogDebtsListProps> = (
    {
        employeeDetails,
        worklogDebts,
        rowNumber,
        handleCheckboxChange,
        isSelected,
        notificationLoadingStatus,
    }) => {
    const [isExpanded, setIsExpanded] = useState(false);

    const toggleExpanded = (event: React.MouseEvent<HTMLDivElement>) => {
        if (event.target instanceof Element
            && (!event.target.closest("#debts-expanded") || event.target.closest("#debts-expanded-hider"))
            && !event.target.closest("#debts-row-actions")) {
            setIsExpanded(!isExpanded);
        }
    }

    return (
        <div className="worklog-debts-list__body-row" onClick={toggleExpanded}>
            <div className="worklog-debts-list__body-row__hidden"
                 style={{backgroundColor: isSelected ? "#f4f6fa" : ""}}
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
                    <RoundCheckbox handleCheckboxChange={handleCheckboxChange}
                                   rowNumber={rowNumber}
                                   checked={isSelected
                                   }/>
                    {(notificationLoadingStatus === NotificationLoadingStatus.Loading) &&
                        <div>Loading</div>
                    }
                    {(notificationLoadingStatus === NotificationLoadingStatus.Pass) &&
                        <div>Pass</div>
                    }
                    {(notificationLoadingStatus === NotificationLoadingStatus.Failed) &&
                        <div>Failed</div>
                    }
                </div>
            </div>
            {isExpanded &&
                <CSSTransition in={isExpanded}
                               timeout={500}
                               classNames="worklog-debts-list__body-row__expanded">
                    <WorklogDebtsExpanded worklogDebts={worklogDebts}/>
                </CSSTransition>
            }
        </div>
    );
};

export default WorklogDebtsItem;