import React, {useEffect, useState} from 'react';
import EmployeeList from "./EmployeeList";
import {Employee} from "../../../models/employee/Employee";
import {useRequest} from "../../../hooks/useRequest";
import EmployeeService from "../../../api/EmployeeService";
import Loader from "../../loader/Loader";
import AddEmployeeModal from "./AddEmployeeModal";
import {Region} from "../../../models/region/Region";
import RegionService from "../../../api/RegionService";

const Employees = () => {
    const [employees, setEmployees] = useState<Employee[]>([]);
    const [regions, setRegions] = useState<Region[]>([]);
    const [isAddEmpModalOpen, setIsAddEmpModalOpen] = useState(false);

    const [fetchEmployees, isEmployeesLoading, fetchEmployeesError] = useRequest(async () => {
        const response = await EmployeeService.getAllEmployees();
        setEmployees([ ...response]);
    });

    const [fetchRegions, isRegionsLoading, fetchRegionsError] = useRequest(async () => {
        const response = await RegionService.getAllRegions()
        setRegions([...response])
    });

    useEffect(() => {
        fetchEmployees();
        fetchRegions();
    }, [])

    const addEmployee = (employee: Employee) => {
        setEmployees([...employees, employee])
    }

    return (
        <div className="content">
            <div className="content__container">
                <div className="content__header">
                    Employees Management
                </div>
                <div className="content__subheader">
                    <div className="content__subheader__text">
                        Manage employees data
                    </div>
                    <button
                        onClick={() => setIsAddEmpModalOpen(true)}
                        className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mr-10 w-[150px]">
                        Add Employee
                    </button>
                </div>
                <AddEmployeeModal
                    addEmployee={addEmployee}
                    isVisible={isAddEmpModalOpen}
                    setIsVisible={setIsAddEmpModalOpen}
                    regions={regions}
                />
                {isEmployeesLoading ?
                    <div className="content__loader">
                        <Loader/>
                    </div>
                    :
                    <EmployeeList
                        employees={employees}
                        setEmployees={setEmployees}
                        regions={regions}
                    />
                }
            </div>
        </div>
    );
};

export default Employees;
