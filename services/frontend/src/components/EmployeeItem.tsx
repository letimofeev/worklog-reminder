import React, {useState} from 'react';
import '../styles/employeeList.scss'
import {Employee} from "../models/employee/Employee";
import EmpNotificationStatus from "./EmpNotificationStatus";
import EditEmployeeModal from "./modal/EditEmployeeModal";
import {Region} from "../models/region/Region";

type EmployeeItemProps = {
    rowNumber: number,
    employee: Employee
    regions: Region[];
}

const EmployeeItem: React.FC<EmployeeItemProps> = ({rowNumber, employee, regions}) => {
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);

    return (
        <div className="employee-list__body-row">
            <EditEmployeeModal
                employee={employee}
                isVisible={isEditModalOpen}
                setIsVisible={setIsEditModalOpen}
                regions={regions}
            />
            <div className="employee-list__no__body-cell">{rowNumber}</div>
            <div className="employee-list__first-name__body-cell">{employee.firstName}</div>
            <div className="employee-list__last-name__body-cell">{employee.lastName}</div>
            <div className="employee-list__region__body-cell">{employee.region.name}</div>
            <div className="employee-list__jira__body-cell">{employee.jiraKey}</div>
            <div className="employee-list__skype__body-cell">{employee.skypeLogin}</div>
            <div className="employee-list__status__body-cell">
                <EmpNotificationStatus
                    notificationEnabled={employee.notificationStatus.notificationEnabled}
                    botConnected={employee.notificationStatus.botConnected}
                />
            </div>
            <div className="employee-list__actions__body-cell">
                <button
                    className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                    onClick={() => setIsEditModalOpen(true)}
                >
                    Edit
                </button>
                <button
                    className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded ml-2"
                >
                    Delete
                </button>
            </div>
        </div>
    );
};

export default EmployeeItem;
