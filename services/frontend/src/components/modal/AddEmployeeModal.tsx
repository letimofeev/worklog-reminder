import React, {useState} from 'react';
import CustomModal from "./CustomModal";
import {Employee} from "../../models/employee/Employee";
import {Region} from "../../models/region/Region";
import {useRequest} from "../../hooks/useRequest";
import EmployeeService from "../../services/EmployeeService";
import FormLoader from "../loader/FormLoader";
import SuccessToast from "../SuccessToast";
import AddEmployeeFormContent from "../AddEmployeeFormContent";
import {CreateEmployeeData} from "../../models/employee/CreateEmployeeData";

type AddEmployeeModalProps = {
    addEmployee: (employee: Employee) => void;
    isVisible: boolean;
    setIsVisible: (isVisible: boolean) => void;
    regions: Region[];
}

const AddEmployeeModal: React.FC<AddEmployeeModalProps> = (
    {
        addEmployee,
        isVisible,
        setIsVisible,
        regions
    }) => {
    const defaultFormData = {
        firstName: '',
        lastName: '',
        jiraKey: '',
        skypeLogin: '',
        regionId: '',
    } as CreateEmployeeData

    const [formData, setFormData] = useState(defaultFormData);
    const [isSuccessToast, setIsSuccessToast] = useState(false);

    const [postEmployee, isLoading, error] = useRequest(async (employee) => {
        setIsSuccessToast(false);
        const response = await EmployeeService.addEmployee(employee);
        setIsSuccessToast(true);
        addEmployee(response);
    })

    const handleClose = () => {
        setFormData(defaultFormData);
        setIsVisible(false);
        setIsSuccessToast(false);
    }

    const handleSubmit = () => {
        postEmployee(formData);
    }

    return (
        <div>
            <CustomModal visible={isVisible} onClose={handleClose}>
                {isLoading ?
                    <FormLoader/>
                    :
                    <AddEmployeeFormContent
                        formData={formData}
                        setFormData={setFormData}
                        onUpdate={handleSubmit}
                        onClose={handleClose}
                        regions={regions}
                    />
                }
                <SuccessToast
                    message="Employee added successfully!"
                    show={isSuccessToast}
                    onHide={() => setIsSuccessToast(false)}
                />
            </CustomModal>
        </div>
    );
};

export default AddEmployeeModal;
