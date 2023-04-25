import React from 'react';

const Employees = () => {
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
                    <button className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">
                        Add Employee
                    </button>
                </div>

                <div className="bg-white rounded px-8 pt-6 pb-8 mb-4">
                    <table className="table-auto w-full mb-4">
                        <thead>
                        <tr>
                            <th className="px-4 py-2 text-left">Name</th>
                            <th className="px-4 py-2 text-left">Email</th>
                            <th className="px-4 py-2 text-left">Department</th>
                            <th className="px-4 py-2 text-left">Position</th>
                            <th className="px-4 py-2 text-left">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {[
                            {
                                id: 10,
                                name: "name",
                                email: "kdskdks@gmail.com",
                                department: "One",
                                position: "Senior"
                            },
                            {
                                id: 11,
                                name: "name 2",
                                email: "kdskdks@gmail.com",
                                department: "One",
                                position: "Senior"
                            }].map((employee) => (
                            <tr key={employee.id}>
                                <td className="border px-4 py-2">{employee.name}</td>
                                <td className="border px-4 py-2">{employee.email}</td>
                                <td className="border px-4 py-2">{employee.department}</td>
                                <td className="border px-4 py-2">{employee.position}</td>
                                <td className="border px-4 py-2">
                                    <button
                                        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                                        Edit
                                    </button>
                                    <button
                                        className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded ml-2">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default Employees;
