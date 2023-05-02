import React, {useEffect, useState} from 'react';
import {Region} from "../../../models/region/Region";
import {useRequest} from "../../../hooks/useRequest";
import RegionService from "../../../api/RegionService";
import CalendarVacationList from "./CalendarVacationList";
import {Employee} from '../../../models/employee/Employee';
import EmployeeVacationList from "./EmployeeVacationList";
import EmployeeService from "../../../api/EmployeeService";
import AddEmployeeVacationModal from "./AddEmployeeVacationModal";

const Vacations = () => {
    const [isCalendarVacations, setIsCalendarVacations] = useState(true);
    const [regions, setRegions] = useState<Region[]>([]);
    const [employees, setEmployees] = useState<Employee[]>([]);
    const [isAddVacModalOpen, setIsAddVacModalOpen] = useState(false);

    const toggleDisplayedVacations = () => {
        setIsCalendarVacations(!isCalendarVacations);
    };

    const [fetchRegions, isRegionsLoading, fetchRegionsError] = useRequest(async () => {
        const response = await RegionService.getAllRegions();
        setRegions([...response]);
    });

    const [fetchEmployees, isEmployeesLoading, fetchEmployeesError] = useRequest(async () => {
        const response = await EmployeeService.getAllEmployees();
        setEmployees([...response]);
    });

    useEffect(() => {
        fetchRegions();
        fetchEmployees();
    }, [])

    return (
        <div className="content">
            <div className="content__container">
                <div className="content__header">
                    Vacations Management
                </div>
                <div className="content__subheader">
                    <div className="content__subheader__text mb-4">
                        Manage vacations
                    </div>
                    {!isCalendarVacations &&
                        <button
                            onClick={() => setIsAddVacModalOpen(true)}
                            className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mr-10 w-[150px]">
                            Add Vacation
                        </button>
                    }
                </div>
                <AddEmployeeVacationModal
                    isVisible={isAddVacModalOpen}
                    setIsVisible={setIsAddVacModalOpen}
                    employees={employees}
                />
                <div className="flex items-center justify-center mb-4">
                    <div
                        onClick={toggleDisplayedVacations}
                        className={`cursor-pointer py-2 px-4 text-white font-bold ${
                            isCalendarVacations ? "bg-gray-700" : "bg-gray-400"
                        } rounded-l`}
                    >
                        Calendar
                    </div>
                    <div
                        onClick={toggleDisplayedVacations}
                        className={`cursor-pointer py-2 px-4 text-white font-bold ${
                            isCalendarVacations ? "bg-gray-400" : "bg-gray-700"
                        } rounded-r`}
                    >
                        Employees
                    </div>
                </div>
                {isCalendarVacations ?
                    <CalendarVacationList
                        regions={regions}
                    />
                    :
                    <EmployeeVacationList
                        employees={employees}
                    />
                }
            </div>

        </div>
    );
};

export default Vacations;