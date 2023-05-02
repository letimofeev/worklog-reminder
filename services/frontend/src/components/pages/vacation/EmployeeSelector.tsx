import React, {useState} from 'react';
import {Employee} from "../../../models/employee/Employee";

type EmployeeSelectorProps = {
    employees: Employee[];
    selectedEmployee: Employee | null;
    setSelectedEmployee: (employee: Employee) => void;
}

const EmployeeSelector: React.FC<EmployeeSelectorProps> = ({employees, selectedEmployee, setSelectedEmployee}) => {
    const [query, setQuery] = useState(getInitialQuery());
    const [filteredEmployees, setFilteredEmployees] = useState<Employee[]>([]);

    function getInitialQuery(): string {
        if (selectedEmployee !== null) {
            return `${selectedEmployee?.firstName} ${selectedEmployee?.lastName}`;
        }
        return '';
    }

    const handleQueryChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const newQuery = event.target.value;
        setQuery(newQuery);

        if (newQuery) {
            const newFilteredEmployees = employees.filter((employee: Employee) => {
                const fullName = `${employee.firstName} ${employee.lastName}`.toLowerCase();
                return fullName.includes(newQuery.toLowerCase());
            });
            setFilteredEmployees(newFilteredEmployees);
        } else {
            setFilteredEmployees([]);
        }
    };

    const handleEmployeeSelect = (employee: Employee) => {
        setSelectedEmployee(employee);
        setQuery(`${employee.firstName} ${employee.lastName}`);
        setFilteredEmployees([]);
    };

    return (
        <div className="employee-selector relative">
            <input
                type="text"
                id="employee-query"
                value={query}
                onChange={handleQueryChange}
                autoComplete="off"
                placeholder="Search for an employee..."
                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            />
            {filteredEmployees.length > 0 && filteredEmployees.length < 10 && (
                <div
                    className="employee-selector__dropdown absolute w-full mt-2 bg-white border border-gray-300 shadow-md z-10">
                    <ul className="list-reset">
                        {filteredEmployees.map((employee) => (
                            <li
                                key={employee.id}
                                onClick={() => handleEmployeeSelect(employee)}
                                className="p-2 hover:bg-gray-200 cursor-pointer"
                            >
                                {employee.firstName} {employee.lastName}
                            </li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
};

export default EmployeeSelector;
