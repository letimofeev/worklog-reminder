import React, {useState} from 'react';
import './worklogDebtsList.scss';
import WorklogDebtItem from "../worklogDebtItem/WorklogDebtItem";
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
        <div className="worklog-debt-list">
            <div className="worklog-debt-list__header">
                <div className="worklog-debt-list__no__header-cell">No</div>
                <div className="worklog-debt-list__employee__header-cell">Employee</div>
                <div className="worklog-debt-list__debts-number__header-cell">Debts Number</div>
                <div className="worklog-debt-list__status__header-cell">
                    <div className="worklog-debt-list__status__header-cell__text-container">
                        Notification Status
                    </div>
                    <div className="worklog-debt-list__status__header-cell__info" onClick={() => setModal(true)}>
                        <FaInfoCircle/>
                    </div>
                    <InfoModal visible={modal} setVisible={setModal}>
                        <EmpNotificationStatusInfo/>
                    </InfoModal>
                </div>
                <div className="worklog-debt-list__actions__header-cell">Actions</div>
            </div>
            <div className="worklog-debt-list__body">
                {employeesDebts.map((employeeDebts, index) => (
                    <WorklogDebtItem
                        key={index}
                        employeeDetails={employeeDebts.employeeDetails}
                        worklogDebts={employeeDebts.worklogDebts}
                        index={index + 1}
                    />
                ))}
            </div>
        </div>
    );
};

export default WorklogDebtsList;
