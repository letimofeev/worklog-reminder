import React, {useState} from 'react';
import EditEmployeeContent from "../EditEmployeeContent";
import CustomModal from "./CustomModal";
import {Employee} from "../../models/employee/Employee";
import {Region} from "../../models/region/Region";
import {useRequest} from "../../hooks/useRequest";
import EmployeeService from "../../services/EmployeeService";
import Loader from "../loader/Loader";
import EmployeeEditSuccess from "../EmployeeEditSuccess";

type EditEmployeeModalProps = {
    employee: Employee;
    isVisible: boolean;
    setIsVisible: (isVisible: boolean) => void;
    regions: Region[];
}

const EditEmployeeModal: React.FC<EditEmployeeModalProps> = ({employee, isVisible, setIsVisible, regions}) => {
    const [formData, setFormData] = useState(employee);
    const [showUpdateResponse, setShowUpdateResponse] = useState(false);

    const [updateEmployee, isEmployeeUpdating, error] = useRequest(async (employee) => {
        await EmployeeService.updateEmployee(employee);
        setShowUpdateResponse(true);
    })


    const handleClose = () => {
        setFormData({...employee});
        setIsVisible(false);
    }

    const handleSubmit = () => {
        updateEmployee(formData);
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
                {/*<div>*/}
                {/*    <button onClick={() => setShowUpdateResponse(true)} className="bg-blue-500 text-white px-4 py-2 rounded">*/}
                {/*        Show Toast*/}
                {/*    </button>*/}

                {/*    {showUpdateResponse && (*/}
                {/*        <div className={updateResponseClasses}>*/}
                {/*            Notification message*/}
                {/*        </div>*/}
                {/*    )}*/}
                {/*</div>*/}
                <EmployeeEditSuccess
                    message="Employee updated successfully!"
                    show={showUpdateResponse}
                    onHide={() => setShowUpdateResponse(false)}
                />
            </CustomModal>
        </div>
    );
};

export default EditEmployeeModal;
