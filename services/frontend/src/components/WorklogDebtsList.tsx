import React, {useState} from 'react';
import '../styles/worklogDebtsList.scss';
import WorklogDebtsItem from "./WorklogDebtsItem";
import {EmployeeDetailsWorklogDebts} from "../models/EmployeeDetailsWorklogDebts";
import {FaInfoCircle} from "react-icons/fa";
import InfoModal from "./modal/InfoModal";
import EmpNotificationStatusInfo from "./EmpNotificationStatusInfo";

type WorklogDebtsListProps = {
    employeesDebts: EmployeeDetailsWorklogDebts[];
}

type SelectedRows = {
    [key: number]: boolean;
};

const WorklogDebtsList: React.FC<WorklogDebtsListProps> = ({employeesDebts}) => {
    const [modal, setModal] = useState(false);
    const [selectedRows, setSelectedRows] = useState<SelectedRows>([]);


    const handleCheckboxChange = (index: number) => {
        setSelectedRows((prevSelectedRows) => ({
            ...prevSelectedRows,
            [index]: !prevSelectedRows[index],
        }));
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
                            rowNumber={index + 1}
                            handleCheckboxChange={handleCheckboxChange}
                            selectedRows={selectedRows}
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
