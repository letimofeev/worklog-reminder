import React, {useState} from 'react';
import CustomModal from "../../modal/CustomModal";
import {Employee} from "../../../models/employee/Employee";
import {useRequest} from "../../../hooks/useRequest";
import FormLoader from "../../loader/FormLoader";
import SuccessToast from "../../SuccessToast";
import AddEmployeeVacationFormContent, {EmployeeVacationFormData} from "./AddEmployeeVacationFormContent";
import {EmployeeVacationFormErrors} from "../../../validation/EmployeeVacationFormErrors";
import VacationService from "../../../api/VacationService";
import {CreateEmployeeVacationData} from "../../../models/vacation/CreateEmployeeVacationData";

type AddEmployeeVacationModalProps = {
    isVisible: boolean;
    setIsVisible: (isVisible: boolean) => void;
    employees: Employee[];
}

const AddEmployeeVacationModal: React.FC<AddEmployeeVacationModalProps> = (
    {
        isVisible,
        setIsVisible,
        employees
    }) => {
    const defaultFormData = {
        employeeId: 0,
        dates: [],
        name: '',
    } as EmployeeVacationFormData

    const [formData, setFormData] = useState(defaultFormData);
    const [formErrors, setFormErrors] = useState<EmployeeVacationFormErrors>({});
    const [isSuccessToast, setIsSuccessToast] = useState(false);
    const [isDateRangePickerVisible, setIsDateRangePickerVisible] = useState(false);

    const [postVacation, isLoading, error] = useRequest(async (vacation: CreateEmployeeVacationData) => {
        await VacationService.addEmployeeVacation(vacation);
    })

    const handleClose = () => {
        setFormData(defaultFormData);
        setIsVisible(false);
        setIsDateRangePickerVisible(false);
        setIsSuccessToast(false);
        setFormErrors({});
    }

    const handleSubmit = (formData: EmployeeVacationFormData) => {
        setIsSuccessToast(false);
        formData.dates.forEach(date => {
            postVacation({
                employeeId: formData.employeeId,
                name: formData.name,
                date: date
            } as CreateEmployeeVacationData);
        });
        setIsSuccessToast(true);
    }

    return (
        <div>
            <CustomModal visible={isVisible} onClose={handleClose}>
                {isLoading ?
                    <FormLoader/>
                    :
                    <AddEmployeeVacationFormContent
                        formData={formData}
                        formErrors={formErrors}
                        setFormErrors={setFormErrors}
                        setFormData={setFormData}
                        onSave={handleSubmit}
                        onClose={handleClose}
                        employees={employees}
                        isDateRangePickerVisible={isDateRangePickerVisible}
                        setIsDateRangePickerVisible={setIsDateRangePickerVisible}
                    />
                }
                <SuccessToast
                    message="Employee vacations added successfully!"
                    show={isSuccessToast}
                    onHide={() => setIsSuccessToast(false)}
                />
            </CustomModal>
        </div>
    );
};

export default AddEmployeeVacationModal;
