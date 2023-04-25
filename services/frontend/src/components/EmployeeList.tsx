import React, {useEffect, useState} from 'react';
import {Employee} from "../models/employee/Employee";
import {FaInfoCircle} from "react-icons/fa";
import CustomModal from "./modal/CustomModal";
import EmpNotificationStatusInfo from "./EmpNotificationStatusInfo";
import '../styles/employeeList.scss';
import '../styles/content.scss';
import EmployeeItem from "./EmployeeItem";
import {Region} from "../models/region/Region";
import {useRequest} from "../hooks/useRequest";
import RegionService from "../services/RegionService";

type EmployeeListProps = {
    employees: Employee[]
}

const EmployeeList: React.FC<EmployeeListProps> = ({employees}) => {
    const [notificationInfoModal, setNotificationInfoModal] = useState(false);
    const [regions, setRegions] = useState<Region[]>([]);

    const [fetchRegions, isRegionsLoading, error] = useRequest(async () => {
        const response = await RegionService.getAllRegions()
        setRegions([...regions, ...response])
    })

    useEffect(() => {
        fetchRegions()
    }, [])


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
        </div>
    );
};

export default EmployeeList;
