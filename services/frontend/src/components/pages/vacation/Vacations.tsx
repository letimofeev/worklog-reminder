import React, {useState} from 'react';

const Vacations = () => {
    const [isCalendarVacations, setIsCalendarVacations] = useState(true);

    const toggleDisplayedVacations = () => {
        setIsCalendarVacations(!isCalendarVacations);
    };

    return (
        <div className="content">
            <div className="content__container">
                <div className="content__header">
                    Vacations Management
                </div>
                <div className="content__subheader">
                    <div className="content__subheader__text">
                        Manage vacations
                    </div>
                </div>
                <div className="flex items-center justify-center">
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
            </div>

        </div>
    );
};

export default Vacations;