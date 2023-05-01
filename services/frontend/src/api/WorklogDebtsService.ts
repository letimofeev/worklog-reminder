import axios from "axios";
import {EmployeeWorklogDebts} from "../models/worklogdebt/EmployeeWorklogDebts";

export default class WorklogDebtsService {
    static async getAllEmployeesDebts() {
        console.log('Fetching api/worklog-debts')
        const response = await axios.get('http://localhost:8080/api/worklog-debts');
        console.log(`Received api/worklog-debts response, debts count: ${response.data.length}`);
        const debts = response.data as EmployeeWorklogDebts[];
        debts.forEach(elem => {
            const login = elem.skypeLogin;
            if (!login) {
                elem.skypeLogin = `anonymous-${elem.id}`;
            }
        });
        return response.data;
    }

    static isLoginPresent(login: string) {
        return !login.startsWith('anonymous-');
    }
}
