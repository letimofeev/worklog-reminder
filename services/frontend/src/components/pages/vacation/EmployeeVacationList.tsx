import React, {useState} from 'react';
import '../../../styles/content.scss';
import '../../../styles/vacationList.scss';
import {DateRangePicker} from 'react-date-range';
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import {addDays} from 'date-fns';
import {useRequest} from "../../../hooks/useRequest";
import VacationService from "../../../api/VacationService";
import VacationItem from "./VacationItem";
import Loader from "../../loader/Loader";
import {Employee} from "../../../models/employee/Employee";
import {EmployeeVacation} from "../../../models/vacation/EmployeeVacation";
import EmployeeSelector from "./EmployeeSelector";

type EmployeeVacationListProps = {
    employees: Employee[];
}

const EmployeeVacationList: React.FC<EmployeeVacationListProps> = ({employees}) => {
    const [vacations, setVacations] = useState<EmployeeVacation[]>([]);
    const [isDateRangePickerCollapsed, setIsDateRangePickerCollapsed] = useState(true);
    const [dateRange, setDateRange] = useState(
        {
            startDate: new Date(),
            endDate: addDays(new Date(), 7),
            key: 'selection',
        },
    );
    const [selectedEmployee, setSelectedEmployee] = useState<Employee | null>(null);

    const [fetchVacations, isVacationsLoading, error] = useRequest(async (dateFrom, dateTo, employeeId) => {
        const response = await VacationService.getEmployeeVacations(dateFrom, dateTo, employeeId);
        setVacations([...response])
    });

    const handleDateRangeClick = (event: React.MouseEvent<HTMLDivElement | HTMLButtonElement>) => {
        event.stopPropagation();
        if (!isDateRangePickerCollapsed) {
            handleDateRangeClose(event);
        } else {
            setIsDateRangePickerCollapsed(false);
        }
    };

    const handleDateRangeClose = (event: React.MouseEvent<HTMLDivElement | HTMLButtonElement>) => {
        event.stopPropagation();
        if (!isDateRangePickerCollapsed) {
            findVacations();
            setIsDateRangePickerCollapsed(true);
        }
    };

    const findVacations = () => {
        const dateFrom = convertToISO(dateRange.startDate);
        const dateTo = convertToISO(dateRange.endDate);

        fetchVacations(dateFrom, dateTo, selectedEmployee?.id);
    };

    const convertToISO = (date: Date) => {
        return date.toLocaleDateString().split('.').reverse().join('-')
    }

    return (
        <div>
            {isVacationsLoading ?
                <div className="content__loader">
                    <Loader/>
                </div>
                :
                <div onClick={e => handleDateRangeClose(e)}
                     className="content-list">
                    <div className="ml-8 mb-4 flex">
                        <div>
                            <EmployeeSelector
                                employees={employees}
                                selectedEmployee={selectedEmployee}
                                setSelectedEmployee={setSelectedEmployee}
                            />
                        </div>
                        <div className="ml-8">
                            <button
                                disabled={selectedEmployee === null}
                                onClick={(e) => handleDateRangeClick(e)}
                                className={`py-2 px-4 text-white font-semibold rounded ${
                                    selectedEmployee === null ? 'bg-blue-300' :
                                        isDateRangePickerCollapsed ? 'bg-blue-500 hover:bg-blue-700' : 'bg-blue-700 hover:bg-blue-800'
                                }`}
                            >
                                Choose dates
                            </button>
                            <div className="collapsable-container">
                                {!isDateRangePickerCollapsed &&
                                    <div
                                        onClick={e => e.stopPropagation()}
                                        className="collapsable-content absolute bg-white z-10 mt-1 p-4 border border-gray-300 rounded">
                                        <DateRangePicker
                                            onChange={(item: any) => setDateRange(item.selection)}
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
                    <div className="content-list__header">
                        <div className="vacation-list__no__header-cell">No</div>
                        <div className="vacation-list__date__header-cell">Date</div>
                        <div className="vacation-list__description__header-cell">Description</div>
                    </div>
                    <div className="content-list__body">
                        {vacations.length
                            ?
                            vacations.map((vacation, index) => (
                                <VacationItem
                                    key={vacation.id}
                                    rowNumber={index + 1}
                                    vacation={vacation}
                                />
                            ))
                            :
                            <div className="content-list__empty">
                                <div className="content-list__empty__text">
                                    No vacations found
                                </div>
                            </div>
                        }
                    </div>
                </div>
            }
        </div>
    );
};

export default EmployeeVacationList;
