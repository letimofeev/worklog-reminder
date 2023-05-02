import React from 'react';
import WorklogDebtItem from "./WorklogDebtItem";
import {DayWorklogDebt} from "../../../models/worklogdebt/DayWorklogDebt";
import {NotificationResponse} from "../../../models/notification/NotificationResponse";
import {NotificationStatus} from "../../../models/notification/NotificationStatus";
import ErrorBlock from "../../error/ErrorBlock";
import WorklogDebtsVacationItem from "./WorklogDebtsVacationItem";
import {ExcludedDay} from "../../../models/worklogdebt/ExcludedDay";

interface WorklogDebtsExpandedProps {
    worklogDebts: DayWorklogDebt[];
    notificationResponse: NotificationResponse;
    excludedDays: ExcludedDay[];
    dateFrom: string;
    dateTo: string;
}

const WorklogDebtsItemExpanded: React.FC<WorklogDebtsExpandedProps> = (
    {
        worklogDebts,
        notificationResponse,
        excludedDays,
        dateFrom,
        dateTo
    }) => {
    return (
        <div id="debts-expanded" className="worklog-debts-list__body-row__expanded">
            <div className="worklog-debts-list__body-row__expanded__container">
                <div className="worklog-debts-list__body-row__expanded__debts-container">
                    {(notificationResponse !== undefined) &&
                        (notificationResponse.status === NotificationStatus.Failed) &&
                        <div className="worklog-debts-list__body-row__expanded__notification-failed">
                            <ErrorBlock
                                header={'Notification failed'}
                                body={notificationResponse.message}
                            />
                        </div>
                    }
                    <div className="worklog-debts-list__body-row__expanded__body__header">
                        Detailed view for period from {dateFrom} to {dateTo}
                    </div>
                    <div className="worklog-debts-list__body-row__expanded__body__table-header">
                        Worklog Debts
                    </div>
                    <div className="worklog-debts-list__body-row__expanded__header">
                        <div className="worklog-debts-list__body-row__expanded__debt-row__no__header-cell">
                            No
                        </div>
                        <div className="worklog-debts-list__body-row__expanded__debt-row__date__header-cell">
                            Date
                        </div>
                        <div className="worklog-debts-list__body-row__expanded__debt-row__time__header-cell">
                            Time debt
                        </div>
                    </div>
                    <div className="worklog-debts-list__body-row__expanded__body__container">
                        {worklogDebts.map((worklogDebt, index) => (
                            <WorklogDebtItem
                                key={index}
                                date={worklogDebt.date}
                                timeDeptSeconds={worklogDebt.timeDeptSeconds}
                                index={index + 1}
                            />
                        ))}
                    </div>
                    <div className="worklog-debts-list__body-row__expanded__body__table-header">
                        Excluded Days
                    </div>
                    <div className="worklog-debts-list__body-row__expanded__header">
                        <div className="worklog-debts-list__body-row__expanded__debt-row__vacation-no__header-cell">
                            No
                        </div>
                        <div className="worklog-debts-list__body-row__expanded__debt-row__vacation-date__header-cell">
                            Date
                        </div>
                        <div
                            className="worklog-debts-list__body-row__expanded__debt-row__vacation-description__header-cell">
                            Description
                        </div>
                    </div>
                    <div className="worklog-debts-list__body-row__expanded__body__container">
                        {excludedDays.map((vacation, index) => (
                            <WorklogDebtsVacationItem
                                key={index}
                                date={vacation.date}
                                description={vacation.reason}
                                index={index + 1}
                            />
                        ))}
                    </div>
                </div>
            </div>

        </div>
    );
};

export default WorklogDebtsItemExpanded;
