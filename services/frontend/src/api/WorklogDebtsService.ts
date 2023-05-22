import axios from "axios";
import {EmployeeWorklogDebts} from "../models/worklogdebt/EmployeeWorklogDebts";

export default class WorklogDebtsService {
    static async getCurrentWeekEmployeesDebts() {
        const response = await axios.get('http://localhost:8080/api/worklog-debts');
        const debts = response.data as EmployeeWorklogDebts[];
        this.handleEmptyLogins(debts);
        return response.data;
    }

    static async getEmployeesDebts(dateFrom: string, dateTo: string) {
        const response = await axios.get(`http://localhost:8080/api/worklog-debts?dateFrom=${dateFrom}&dateTo=${dateTo}`);
        const debts = response.data as EmployeeWorklogDebts[];
        this.handleEmptyLogins(debts);
        return response.data;
    }

    static handleEmptyLogins(debts: EmployeeWorklogDebts[]) {
        debts.forEach(elem => {
            const login = elem.skypeLogin;
            if (!login) {
                elem.skypeLogin = `anonymous-${elem.id}`;
            }
        });
    }

    static isLoginPresent(login: string) {
        return !login.startsWith('anonymous-');
    }
}
