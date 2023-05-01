import React, {useState} from 'react';
import '../../../styles/employeeList.scss'
import {Employee} from "../../../models/employee/Employee";
import EmpNotificationStatus from "./EmpNotificationStatus";
import EditEmployeeModal from "./EditEmployeeModal";
import {Region} from "../../../models/region/Region";
import EmployeeService from "../../../api/EmployeeService";
import {useRequest} from "../../../hooks/useRequest";
import Loader from "../../loader/Loader";

type EmployeeItemProps = {
    rowNumber: number,
    employee: Employee
    setEmployee: (employee: Employee) => void;
    deleteEmployee: (employee: Employee) => void;
    regions: Region[];
}

const EmployeeItem: React.FC<EmployeeItemProps> = (
    {
        rowNumber,
        employee,
        setEmployee,
        deleteEmployee,
        regions,
    }) => {
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);

    const [removeEmployee, isDeleteLoading, error] = useRequest(async (employee: Employee) => {
        await EmployeeService.deleteEmployee(employee);
        deleteEmployee(employee);
    })

    return (
        <div className="employee-list__body-row">
            <EditEmployeeModal
                employee={employee}
                setEmployee={setEmployee}
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
                    onClick={() => setIsEditModalOpen(true)}
                    className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                >
                    Edit
                </button>
                <div className="inline-flex items-center ml-2 relative">
                    <button
                        onClick={() => removeEmployee(employee)}
                        className={`bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded ${
                            isDeleteLoading ? 'opacity-0' : ''
                        }`}
                    >
                        Delete
                    </button>
                    {isDeleteLoading && (
                        <div className="absolute inset-0 flex items-center justify-center">
                            <Loader style={
                                {
                                    height: '25px',
                                    width: '25px',
                                    border: '7px solid #f4f6fa',
                                    borderTop: '7px solid #3498db'
                                }}/>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default EmployeeItem;
