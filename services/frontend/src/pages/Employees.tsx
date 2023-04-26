import React, {useEffect, useState} from 'react';
import EmployeeList from "../components/EmployeeList";
import {Employee} from "../models/employee/Employee";
import {useRequest} from "../hooks/useRequest";
import EmployeeService from "../services/EmployeeService";
import Loader from "../components/loader/Loader";

const Employees = () => {
    const [employees, setEmployees] = useState<Employee[]>([]);
    const [isInitialLoad, setIsInitialLoad] = useState(true);

    const [fetchEmployees, isEmployeesLoading, error] = useRequest(async () => {
        const response = await EmployeeService.getAllEmployees();
        setEmployees([...employees, ...response]);
        setIsInitialLoad(false);
    });

    useEffect(() => {
        fetchEmployees()
    }, [])

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
                    <button className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mr-10">
                        Add Employee
                    </button>
                </div>
                {isEmployeesLoading || isInitialLoad ?
                    <div className="content__loader">
                        <Loader/>
                    </div>
                    :
                    <EmployeeList
                        employees={employees}
                        setEmployees={setEmployees}
                    />
                }
            </div>
        </div>
    );
};

export default Employees;
