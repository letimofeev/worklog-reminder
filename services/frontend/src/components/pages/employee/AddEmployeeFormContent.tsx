import React, {Dispatch, SetStateAction} from "react";
import {Region} from "../../../models/region/Region";
import {CreateEmployeeData} from "../../../models/employee/CreateEmployeeData";
import {EmployeeFormValidator} from "../../../validation/EmployeeFormValidator";
import {EmployeeFormErrors} from "../../../validation/EmployeeFormErrors";

type AddEmployeeContentProps = {
    formData: CreateEmployeeData;
    formErrors: EmployeeFormErrors;
    setFormErrors: (formErrors: EmployeeFormErrors) => void;
    setFormData: Dispatch<SetStateAction<CreateEmployeeData>>;
    onUpdate: (createdEmployee: CreateEmployeeData) => void;
    onClose: () => void;
    regions: Region[];
}

const AddEmployeeFormContent: React.FC<AddEmployeeContentProps> = (
    {
        formData,
        formErrors,
        setFormErrors,
        setFormData,
        onUpdate,
        onClose,
        regions
    }) => {
    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const trimmedFormData = trimEmployeeData(formData);
        setFormData(trimmedFormData)
        if (validateForm(trimmedFormData)) {
            onUpdate(formData);
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        setFormData({...formData, [e.target.name]: e.target.value});
    };

    const trimEmployeeData = (employeeData: CreateEmployeeData): CreateEmployeeData => {
        return {
            firstName: employeeData.firstName.trim(),
            lastName: employeeData.lastName.trim(),
            jiraKey: employeeData.jiraKey.trim(),
            skypeLogin: employeeData.skypeLogin.trim(),
            regionId: employeeData.regionId.trim(),
        };
    };

    const validateForm = (formData: CreateEmployeeData) => {
        let [isValid, errors] = EmployeeFormValidator.validateForm(formData);
        setFormErrors(errors);
        return isValid;
    };

    return (
        <form onSubmit={handleSubmit} className="bg-white rounded px-8 pt-6 pb-8 w-[700px] h-[500px]">
            <h2 className="text-xl font-bold mb-4">Add Employee</h2>
            <p className="mb-6 text-gray-600">Add the employee with specified data</p>
            <div className="space-y-6">
                <div className="grid grid-cols-2 gap-4 mb-4">
                    <div>
                        <label htmlFor="firstName" className="block text-gray-700 text-sm font-bold mb-2">
                            First Name
                        </label>
                        <input
                            type="text"
                            name="firstName"
                            id="firstName"
                            value={formData.firstName}
                            onChange={handleChange}
                            className={`appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:border-indigo-500" ${
                                formErrors.firstName ? 'border-red-500' : 'border-gray-200'
                            }`}
                        />
                        {formErrors.firstName &&
                            <div className="absolute mt-1 text-xs text-red-500">
                                {formErrors.firstName}
                            </div>
                        }
                    </div>
                    <div>
                        <label htmlFor="lastName" className="block text-gray-700 text-sm font-bold mb-2">
                            Last Name
                        </label>
                        <input
                            type="text"
                            name="lastName"
                            id="lastName"
                            value={formData.lastName}
                            onChange={handleChange}
                            className={`appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:border-indigo-500" ${
                                formErrors.lastName ? 'border-red-500' : 'border-gray-200'
                            }`}
                        />
                        {formErrors.lastName &&
                            <div className="absolute mt-1 text-xs text-red-500">
                                {formErrors.lastName}
                            </div>
                        }
                    </div>
                </div>
                <div className="grid grid-cols-2 gap-4 mb-4">
                    <div>
                        <label htmlFor="jiraKey" className="block text-gray-700 text-sm font-bold mb-2">
                            Jira Key
                        </label>
                        <input
                            type="text"
                            name="jiraKey"
                            id="jiraKey"
                            value={formData.jiraKey}
                            onChange={handleChange}
                            className={`appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:border-indigo-500" ${
                                formErrors.jiraKey ? 'border-red-500' : 'border-gray-200'
                            }`}
                        />
                        {formErrors.jiraKey &&
                            <div className="absolute mt-1 text-xs text-red-500">
                                {formErrors.jiraKey}
                            </div>
                        }
                    </div>
                    <div>
                        <label htmlFor="skypeLogin" className="block text-gray-700 text-sm font-bold mb-2">
                            Skype Login
                        </label>
                        <input
                            type="text"
                            name="skypeLogin"
                            id="skypeLogin"
                            value={formData.skypeLogin}
                            onChange={handleChange}
                            className={`appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:border-indigo-500" ${
                                formErrors.skypeLogin ? 'border-red-500' : 'border-gray-200'
                            }`}
                        />
                        {formErrors.skypeLogin &&
                            <div className="absolute mt-1 text-xs text-red-500">
                                {formErrors.skypeLogin}
                            </div>
                        }
                    </div>
                </div>
                <div className="grid grid-cols-2 gap-4">
                    <div className="mb-4">
                        <label htmlFor="region" className="block text-gray-700 text-sm font-bold mb-2">
                            Region
                        </label>
                        <div className="relative">
                            <select
                                name="regionId"
                                id="regionId"
                                value={formData.regionId}
                                onChange={handleChange}
                                className={`appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:border-indigo-500" ${
                                    formErrors.regionId ? 'border-red-500' : 'border-gray-200'
                                }`}
                            >
                                <option value="" disabled>Select a region</option>
                                {regions.map((region) => (
                                    <option key={region.id} value={region.id}>
                                        {region.name}
                                    </option>
                                ))}
                            </select>
                            {formErrors.regionId &&
                                <div className="absolute mt-1 text-xs text-red-500">
                                    {formErrors.regionId}
                                </div>
                            }
                        </div>
                    </div>
                </div>
            </div>
            <div className="flex items-center justify-end mt-6 space-x-4">
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

export default AddEmployeeFormContent;
