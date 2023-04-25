import React, {Dispatch, SetStateAction, useState} from 'react';
import '../styles/worklogDebtsList.scss';
import WorklogDebtsItem from "./WorklogDebtsItem";
import {EmployeeWorklogDebts} from "../models/worklogdebt/EmployeeWorklogDebts";
import {FaInfoCircle} from "react-icons/fa";
import InfoModal from "./modal/InfoModal";
import EmpNotificationStatusInfo from "./EmpNotificationStatusInfo";
import {TransitionGroup} from "react-transition-group";
import {NotificationLoadingRows, NotificationResponses} from "../pages/WorklogDebts";

type WorklogDebtsListProps = {
    employeesDebts: EmployeeWorklogDebts[];
    selectedRows: boolean[];
    setSelectedRows: Dispatch<SetStateAction<boolean[]>>;
    notificationLoadingRows: NotificationLoadingRows;
    notificationResponses: NotificationResponses;
}

const WorklogDebtsList: React.FC<WorklogDebtsListProps> = (
    {
        employeesDebts,
        selectedRows,
        setSelectedRows,
        notificationLoadingRows,
        notificationResponses
    }) => {
    const [modal, setModal] = useState(false);

    const toggleSelected = (index: number) => {
        selectedRows[index] = !selectedRows[index];
        setSelectedRows([...selectedRows]);
    };

    const setIsSelected = (index: number, value: boolean) => {
        selectedRows[index] = value;
        setSelectedRows([...selectedRows]);
    };

    return (
        <div className="worklog-debts-list">
            <div className="worklog-debts-list__header">
                <div className="worklog-debts-list__no__header-cell">No</div>
                <div className="worklog-debts-list__employee__header-cell">Employee</div>
                <div className="worklog-debts-list__debts-number__header-cell">Debts Number</div>
                <div className="worklog-debts-list__status__header-cell">
                    <div className="worklog-debts-list__status__header-cell__text-container">
                        Notification Status
                    </div>
                    <div className="worklog-debts-list__status__header-cell__info" onClick={() => setModal(true)}>
                        <div className="flex items-center justify-center h-full">
                            <FaInfoCircle />
                        </div>
                    </div>
                    <InfoModal visible={modal} setVisible={setModal}>
                        <EmpNotificationStatusInfo/>
                    </InfoModal>
                </div>
                <div className="worklog-debts-list__actions__header-cell">Actions</div>
            </div>
            <div className="worklog-debts-list__body">
                {employeesDebts.length
                    ?
                    <TransitionGroup style={{
                        width: "100%"
                    }}>
                        {employeesDebts.map((employeeDebts, index) => (
                            <WorklogDebtsItem
                                key={employeeDebts.id}
                                employeeDebts={employeeDebts}
                                rowNumber={index + 1}
                                toggleSelected={toggleSelected}
                                setIsSelected={setIsSelected}
                                isSelected={selectedRows[index]}
                                notificationLoadingStatus={notificationLoadingRows[employeeDebts.skypeLogin]}
                                notificationResponse={notificationResponses[employeeDebts.skypeLogin]}
                            />
                        ))}
                    </TransitionGroup>
                    :
                    <div className="worklog-debts-list__empty">
                        <div className="worklog-debts-list__empty__text">
                            No debts found
                        </div>
                        <div className="worklog-debts-list__empty__image"
                             style={{backgroundImage: "url(/images/happy-cat-sticker.png)"}}/>
                    </div>
                }
            </div>
        </div>
    );
};

export default WorklogDebtsList;
