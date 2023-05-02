import axios from "axios";
import {CalendarVacation} from "../models/vacation/CalendarVacation";
import {EmployeeVacation} from "../models/vacation/EmployeeVacation";
import {CreateEmployeeVacationData} from "../models/vacation/CreateEmployeeVacationData";

export default class VacationService {
    static async getCalendarVacations(dateFrom: string, dateTo: string, regionId: string): Promise<CalendarVacation[]> {
        console.log('GET api/vacations/calendar/region');
        const response = await axios.get(`http://localhost:8080/api/vacations/calendar/region/${regionId}?dateFrom=${dateFrom}&dateTo=${dateTo}`);
        return response.data;
    }

    static async getEmployeeVacations(dateFrom: string, dateTo: string, employeeId: string): Promise<EmployeeVacation[]> {
        console.log('GET api/vacations/employee');
        const response = await axios.get(`http://localhost:8080/api/vacations/employee/${employeeId}?dateFrom=${dateFrom}&dateTo=${dateTo}`);
        return response.data;
    }

    static async addEmployeeVacation(createVacation: CreateEmployeeVacationData): Promise<EmployeeVacation> {
        console.log('POST api/employees');
        const response = await axios.post('http://localhost:8080/api/vacations/employee', createVacation);
        return response.data;
    }
}
