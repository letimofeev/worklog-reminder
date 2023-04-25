import React, {useEffect, useState} from 'react';
import EditEmployeeContent from "../EditEmployeeContent";
import CustomModal from "./CustomModal";
import {Employee} from "../../models/employee/Employee";
import {Region} from "../../models/region/Region";
import {useRequest} from "../../hooks/useRequest";
import RegionService from "../../services/RegionService";
import EmployeeService from "../../services/EmployeeService";
import Loader from "../loader/Loader";

type EditEmployeeModalProps = {
    employee: Employee;
    isVisible: boolean;
    setIsVisible: any;
    regions: Region[];
}

const EditEmployeeModal: React.FC<EditEmployeeModalProps> = ({employee, isVisible, setIsVisible, regions}) => {
    const [formData, setFormData] = useState(employee);

    const [updateEmployee, isEmployeeUpdating, error] = useRequest(async (employee) => {
        await EmployeeService.updateEmployee(employee);
    })

    const handleClose = () => {
        setFormData({...employee});
        setIsVisible(false);
    }

    const handleSubmit = () => {
        updateEmployee(formData)
    }

    return (
        <div>
            <CustomModal visible={isVisible} onClose={handleClose}>
                {isEmployeeUpdating ?
                    <div className="h-[500px] w-[700px] flex items-center justify-center">
                        <div className="w-full flex items-center justify-center">
                            <Loader/>
                        </div>
                    </div>
                    :
                    <EditEmployeeContent
                        formData={formData}
                        setFormData={setFormData}
                        onUpdate={handleSubmit}
                        onClose={handleClose}
                        regions={regions}
                    />
                }
            </CustomModal>
        </div>
    );
};

export default EditEmployeeModal;
