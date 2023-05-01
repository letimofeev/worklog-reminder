import React, {useState} from 'react';
import CustomModal from "../../modal/CustomModal";
import {Employee} from "../../../models/employee/Employee";
import {Region} from "../../../models/region/Region";
import {useRequest} from "../../../hooks/useRequest";
import EmployeeService from "../../../api/EmployeeService";
import EditEmployeeFormContent from "./EditEmployeeFormContent";
import FormLoader from "../../loader/FormLoader";
import SuccessToast from "../../SuccessToast";
import {UpdateEmployeeData} from "../../../models/employee/UpdateEmployeeData";
import {EmployeeFormErrors} from "../../../validation/EmployeeFormErrors";

type EditEmployeeModalProps = {
    employee: Employee;
    setEmployee: (employee: Employee) => void;
    isVisible: boolean;
    setIsVisible: (isVisible: boolean) => void;
    regions: Region[];
}

const EditEmployeeModal: React.FC<EditEmployeeModalProps> = (
    {
        employee,
        setEmployee,
        isVisible,
        setIsVisible,
        regions
    }) => {
    const getFormData = (emp: Employee): UpdateEmployeeData => {
        return {
            id: emp.id,
            firstName: emp.firstName,
            lastName: emp.lastName,
            jiraKey: emp.jiraKey,
            skypeLogin: emp.skypeLogin,
            regionId: emp.region.id,
            notificationEnabled: emp.notificationStatus.notificationEnabled
        }
    }

    const [formData, setFormData] = useState(getFormData(employee));
    const [formErrors, setFormErrors] = useState<EmployeeFormErrors>({});
    const [isSuccessToast, setIsSuccessToast] = useState(false);

    const [updateEmployee, isEmployeeUpdating, error] = useRequest(async (employee) => {
        setIsSuccessToast(false);
        const response = await EmployeeService.updateEmployee(employee);
        setIsSuccessToast(true);
        setEmployee(response);
    })

    const handleClose = () => {
        setFormData(getFormData(employee));
        setIsVisible(false);
        setIsSuccessToast(false);
        setFormErrors({});
    }

    const handleSubmit = () => {
        updateEmployee(formData);
    }

    return (
        <div>
            <CustomModal visible={isVisible} onClose={handleClose}>
                {isEmployeeUpdating ?
                    <FormLoader/>
                    :
                    <EditEmployeeFormContent
                        formData={formData}
                        setFormData={setFormData}
                        botConnected={employee.notificationStatus.botConnected}
                        formErrors={formErrors}
                        setFormErrors={setFormErrors}
                        onUpdate={handleSubmit}
                        onClose={handleClose}
                        regions={regions}
                    />
                }
                <SuccessToast
                    message="Employee updated successfully!"
                    show={isSuccessToast}
                    onHide={() => setIsSuccessToast(false)}
                />
            </CustomModal>
        </div>
    );
};

export default EditEmployeeModal;
