import React, {useState} from 'react';
import {Employee} from "../../../models/employee/Employee";
import {FaInfoCircle} from "react-icons/fa";
import CustomModal from "../../modal/CustomModal";
import EmpNotificationStatusInfo from "./EmpNotificationStatusInfo";
import '../../../styles/employeeList.scss';
import '../../../styles/content.scss';
import EmployeeItem from "./EmployeeItem";
import {Region} from "../../../models/region/Region";
import SuccessToast from "../../SuccessToast";

type EmployeeListProps = {
    employees: Employee[];
    setEmployees: (employees: Employee[]) => void;
    regions: Region[];
}

const EmployeeList: React.FC<EmployeeListProps> = ({employees, setEmployees, regions}) => {
    const [notificationInfoModal, setNotificationInfoModal] = useState(false);
    const [isEmpDeletedSuccessToast, setIsEmpDeletedSuccessToast] = useState(false);

    const setEmployee = (employee: Employee) => {
        const index = employees.findIndex(emp => emp.id === employee.id);
        const updatedEmployees = [
            ...employees.slice(0, index),
            employee,
            ...employees.slice(index + 1)
        ];
        setEmployees(updatedEmployees);
    }

    const deleteEmployee = (employee: Employee) => {
        setIsEmpDeletedSuccessToast(false);
        setEmployees(employees.filter(emp => emp.id !== employee.id));
        setIsEmpDeletedSuccessToast(true);
    }

    return (
        <div className="content-list">
            <div className="content-list__header">
                <div className="employee-list__no__header-cell">No</div>
                <div className="employee-list__first-name__header-cell">First Name</div>
                <div className="employee-list__last-name__header-cell">Last Name</div>
                <div className="employee-list__region__header-cell">Region</div>
                <div className="employee-list__jira__header-cell">Jira Key</div>
                <div className="employee-list__skype__header-cell">Skype Login</div>
                <div className="employee-list__status__header-cell">
                    <div className="employee-list__status__header-cell__text-container">
                        Notification Status
                    </div>
                    <div className="employee-list__status__header-cell__info" onClick={() => setNotificationInfoModal(true)}>
                        <div className="flex items-center justify-center h-full">
                            <FaInfoCircle/>
                        </div>
                    </div>
                    <CustomModal visible={notificationInfoModal} onClose={() => setNotificationInfoModal(false)}>
                        <EmpNotificationStatusInfo/>
                    </CustomModal>
                </div>
                <div className="employee-list__actions__header-cell">Actions</div>
            </div>
            <div className="content-list__body">
                {employees.length
                    ?
                    employees.map((employee, index) => (
                        <EmployeeItem
                            key={employee.id}
                            rowNumber={index + 1}
                            employee={employee}
                            setEmployee={setEmployee}
                            deleteEmployee={deleteEmployee}
                            regions={regions}
                        />
                    ))
                    :
                    <div className="employee-list__empty">
                        <div className="employee-list__empty__text">
                            No employees found
                        </div>
                    </div>
                }
            </div>
            <SuccessToast
                message={"Employee deleted successfully!"}
                show={isEmpDeletedSuccessToast}
                onHide={() => setIsEmpDeletedSuccessToast(false)}
            />
        </div>
    );
};

export default EmployeeList;
