import React, {useEffect, useState} from 'react';
import EditEmployeeContent from "../EditEmployeeContent";
import CustomModal from "./CustomModal";
import {Employee} from "../../models/employee/Employee";
import {Region} from "../../models/region/Region";

type EditEmployeeModalProps = {
    employee: Employee;
    isVisible: boolean;
    setIsVisible: any;
    regions: Region[];
}

const EditEmployeeModal: React.FC<EditEmployeeModalProps> = ({employee, isVisible, setIsVisible, regions}) => {
    const [formData, setFormData] = useState(employee);

    const handleClose = () => {
        setFormData({...employee});
        setIsVisible(false);
    }

    return (
        <div>
            <CustomModal visible={isVisible} onClose={handleClose}>
                <EditEmployeeContent
                    formData={formData}
                    setFormData={setFormData}
                    onUpdate={() => console.log("update")}
                    onClose={handleClose}
                    regions={regions}
                />
            </CustomModal>
        </div>
    );
};

export default EditEmployeeModal;