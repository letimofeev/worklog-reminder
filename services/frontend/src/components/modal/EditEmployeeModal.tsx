import React, {useState} from 'react';
import CustomModal from "./CustomModal";
import {Employee} from "../../models/employee/Employee";
import {Region} from "../../models/region/Region";
import {useRequest} from "../../hooks/useRequest";
import EmployeeService from "../../services/EmployeeService";
import EditEmployeeFormContent from "../EditEmployeeFormContent";
import FormLoader from "../loader/FormLoader";
import SuccessToast from "../SuccessToast";
import {UpdateEmployeeData} from "../../models/employee/UpdateEmployeeData";

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
                        botConnected={employee.notificationStatus.botConnected}
                        setFormData={setFormData}
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
