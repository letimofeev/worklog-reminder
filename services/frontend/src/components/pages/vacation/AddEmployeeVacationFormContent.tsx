import React, {useState} from "react";
import {EmployeeVacationFormErrors} from "../../../validation/EmployeeVacationFormErrors";
import {Employee} from "../../../models/employee/Employee";
import {EmployeeVacationFormValidator} from "../../../validation/EmployeeVacationFormValidator";
import EmployeeSelector from "./EmployeeSelector";
import {DateRangePicker} from "react-date-range";
import {addDays} from "date-fns";

export interface EmployeeVacationFormData {
    employeeId: number;
    dates: string[];
    name: string;
}

type AddEmployeeVacationContentProps = {
    formData: EmployeeVacationFormData;
    formErrors: EmployeeVacationFormErrors;
    setFormErrors: (formErrors: EmployeeVacationFormErrors) => void;
    setFormData: (formData: EmployeeVacationFormData) => void;
    isDateRangePickerVisible: boolean;
    setIsDateRangePickerVisible: (value: boolean) => void;
    onSave: (formData: EmployeeVacationFormData) => void;
    onClose: () => void;
    employees: Employee[];
}

const AddEmployeeVacationFormContent: React.FC<AddEmployeeVacationContentProps> = (
    {
        formData,
        formErrors,
        setFormErrors,
        setFormData,
        isDateRangePickerVisible,
        setIsDateRangePickerVisible,
        onSave,
        onClose,
        employees
    }) => {
    const [selectedEmployee, setSelectedEmployee] = useState<Employee | null>(null);
    const [dateRange, setDateRange] = useState(
        {
            startDate: new Date(),
            endDate: addDays(new Date(), 7),
            key: 'selection',
        },
    );

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const trimmedFormData = trimVacationData(formData);
        setFormData(trimmedFormData);
        if (validateForm(trimmedFormData)) {
            const dates = generateDatesList(dateRange.startDate, dateRange.endDate);
            const formDataWithDates = {...formData, dates: dates};
            setFormData(formDataWithDates);
            onSave(formDataWithDates);
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setFormData({...formData, [e.target.name]: e.target.value});
    };

    const trimVacationData = (vacation: EmployeeVacationFormData): EmployeeVacationFormData => {
        return {...vacation, name: vacation.name.trim()};
    };

    const validateForm = (formData: EmployeeVacationFormData) => {
        let [isValid, errors] = EmployeeVacationFormValidator.validateForm(formData,
            dateRange.startDate, dateRange.endDate);
        setFormErrors(errors);
        return isValid;
    };

    const handleDateRangeChange = (item: any) => {
        setDateRange(item.selection);
    }

    const generateDatesList = (startDate: Date, endDate: Date): string[] => {
        const dates = [];
        let currentDate = new Date(startDate);

        while (currentDate <= endDate) {
            dates.push(new Date(currentDate).toLocaleDateString().split('.').reverse().join('-'));
            currentDate.setDate(currentDate.getDate() + 1);
        }

        return dates;
    };

    const handleSetSelectedEmployee = (employee: Employee) => {
        setSelectedEmployee(employee);
        setFormData({...formData, employeeId: employee.id})
    }

    return (
        <form onSubmit={handleSubmit} className="bg-white rounded px-8 pt-6 pb-8 w-[700px] h-[300px]">
            <h2 className="text-xl font-bold mb-4">Add Vacation</h2>
            <p className="mb-6 text-gray-600">Add the vacation for specified employee</p>
            <div className="space-y-6">
                <div>
                    <div className="grid grid-cols-2 gap-4">
                        <div>
                            <input
                                type="text"
                                name="name"
                                id="name"
                                placeholder="Description"
                                value={formData.name}
                                onChange={handleChange}
                                className={`appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:border-indigo-500" ${
                                    formErrors.name ? 'border-red-500' : 'border-gray-200'
                                }`}
                            />
                            {formErrors.name &&
                                <div className="absolute mt-1 text-xs text-red-500">
                                    {formErrors.name}
                                </div>
                            }
                        </div>
                        <div>
                            <button
                                type="button"
                                onClick={() => setIsDateRangePickerVisible(!isDateRangePickerVisible)}
                                className={`py-2 px-4 text-white font-semibold rounded ${
                                    isDateRangePickerVisible ? 'bg-blue-700 hover:bg-blue-800' : 'bg-blue-500 hover:bg-blue-700'
                                }`}
                            >
                                Choose dates
                            </button>
                            {formErrors.date &&
                                <div className="absolute mt-1 text-xs text-red-500">
                                    {formErrors.date}
                                </div>
                            }
                            <div className="collapsable-container">
                                {isDateRangePickerVisible &&
                                    <div
                                        onClick={e => e.stopPropagation()}
                                        className="collapsable-content absolute bg-white z-10 mt-1 p-4 border border-gray-300 rounded">
                                        <DateRangePicker
                                            onChange={item => handleDateRangeChange(item)}
                                            showPreview={true}
                                            moveRangeOnFirstSelection={false}
                                            months={1}
                                            ranges={[dateRange]}
                                            direction="horizontal"
                                        />
                                    </div>
                                }
                            </div>
                        </div>
                    </div>
                </div>
                <div className="grid grid-cols-2 gap-4">
                    <div className="mb-4">
                        <EmployeeSelector
                            employees={employees}
                            selectedEmployee={selectedEmployee}
                            setSelectedEmployee={handleSetSelectedEmployee}
                        />
                        {formErrors.employeeId &&
                            <div className="absolute mt-1 text-xs text-red-500">
                                {formErrors.employeeId}
                            </div>
                        }
                    </div>
                </div>
            </div>
            <div className="flex items-center justify-end mt-6 space-x-4 mb-4">
                <button
                    type="submit"
                    className="bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:border-indigo-700"
                >
                    Save
                </button>
                <button
                    type="button"
                    onClick={onClose}
                    className="bg-white hover:bg-gray-100 text-gray-700 font-bold py-2 px-4 rounded border border-gray-300 focus:outline-none focus:border-indigo-500"
                >
                    Cancel
                </button>
            </div>
        </form>
    );
};

export default AddEmployeeVacationFormContent;
