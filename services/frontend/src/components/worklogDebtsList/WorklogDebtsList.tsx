import React, {useState} from 'react';
import './worklogDebtsList.scss';
import WorklogDebtsItem from "../worklogDebtsItem/WorklogDebtsItem";
import {EmployeeDetailsWorklogDebts} from "../../models/EmployeeDetailsWorklogDebts";
import {FaInfoCircle} from "react-icons/fa";
import InfoModal from "../infoModal/InfoModal";
import EmpNotificationStatusInfo from "../empNotificationStatusInfo/EmpNotificationStatusInfo";

interface WorklogDebtsListProps {
    employeesDebts: EmployeeDetailsWorklogDebts[];
}

const WorklogDebtsList: React.FC<WorklogDebtsListProps> = ({employeesDebts}) => {
    const [modal, setModal] = useState(false)

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
                        <FaInfoCircle/>
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
                    employeesDebts.map((employeeDebts, index) => (
                        <WorklogDebtsItem
                            key={index}
                            employeeDetails={employeeDebts.employeeDetails}
                            worklogDebts={employeeDebts.worklogDebts}
                            index={index + 1}
                        />
                    ))
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
