import React, {useState} from 'react';
import '../../../styles/content.scss';
import '../../../styles/vacationList.scss';
import {Region} from "../../../models/region/Region";
import {CalendarVacation} from "../../../models/vacation/CalendarVacation";
import {DateRangePicker} from 'react-date-range';
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import {addDays} from 'date-fns';
import {useRequest} from "../../../hooks/useRequest";
import VacationService from "../../../api/VacationService";
import VacationItem from "./VacationItem";
import Loader from "../../loader/Loader";

type CalendarVacationListProps = {
    regions: Region[];
}

const CalendarVacationList: React.FC<CalendarVacationListProps> = ({regions}) => {
    const [vacations, setVacations] = useState<CalendarVacation[]>([]);
    const [selectedRegionId, setSelectedRegionId] = useState('');
    const [isDateRangePickerVisible, setIsDateRangePickerVisible] = useState(false);
    const [dateRange, setDateRange] = useState(
        {
            startDate: new Date(),
            endDate: addDays(new Date(), 7),
            key: 'selection',
        },
    );

    const [fetchVacations, isVacationsLoading, error] = useRequest(async (dateFrom, dateTo, regionId) => {
        const response = await VacationService.getCalendarVacations(dateFrom, dateTo, regionId);
        setVacations([...response])
    });

    const handleRegionChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedRegionId(e.target.value);
    };

    const handleDateRangeClick = (event: React.MouseEvent<HTMLDivElement | HTMLButtonElement>) => {
        event.stopPropagation();
        if (isDateRangePickerVisible) {
            handleDateRangeClose(event);
        } else {
            setIsDateRangePickerVisible(true);
        }
    };

    const handleDateRangeClose = (event: React.MouseEvent<HTMLDivElement | HTMLButtonElement>) => {
        event.stopPropagation();
        if (isDateRangePickerVisible) {
            findVacations();
            setIsDateRangePickerVisible(false);
        }
    };

    const findVacations = () => {
        const dateFrom = convertToISO(dateRange.startDate);
        const dateTo = convertToISO(dateRange.endDate);

        fetchVacations(dateFrom, dateTo, selectedRegionId);
    };

    const convertToISO = (date: Date) => {
        return date.toLocaleDateString().split('.').reverse().join('-')
    }

    return (
        <div>
            <div onClick={e => handleDateRangeClose(e)}
                 className="content-list">
                <div className="relative">
                    <div className="ml-8 flex">
                        <div className="mb-4 w-[200px]">
                            <select
                                name="regionId"
                                id="regionId"
                                value={selectedRegionId}
                                onChange={handleRegionChange}
                                className="appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:border-indigo-500 border-gray-200"
                            >
                                <option value="" disabled>Select a region</option>
                                {regions.map((region) => (
                                    <option key={region.id} value={region.id}>
                                        {region.name}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="ml-8">
                            <button
                                disabled={selectedRegionId === ''}
                                onClick={(e) => handleDateRangeClick(e)}
                                className={`py-2 px-4 text-white font-semibold rounded ${
                                    selectedRegionId === '' ? 'bg-blue-300' :
                                        isDateRangePickerVisible ? 'bg-blue-700 hover:bg-blue-800' : 'bg-blue-500 hover:bg-blue-700'
                                }`}
                            >
                                Choose dates
                            </button>
                            <div className="collapsable-container">
                                {isDateRangePickerVisible &&
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
                </div>
                <div className="content-list__header">
                    <div className="vacation-list__no__header-cell">No</div>
                    <div className="vacation-list__date__header-cell">Date</div>
                    <div className="vacation-list__description__header-cell">Description</div>
                </div>
                <div className="content-list__body">
                    {isVacationsLoading ?
                        <div className="content__loader">
                            <Loader/>
                        </div>
                        :
                        vacations.length
                            ?
                            vacations.map((vacation, index) => (
                                <VacationItem
                                    key={vacation.region.id + '_' + vacation.date}
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
        </div>
    );
};

export default CalendarVacationList;
