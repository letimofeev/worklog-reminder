import axios from "axios";
import {EmployeeDetailsWorklogDebts} from "../models/worklogdebt/EmployeeDetailsWorklogDebts";

export default class WorklogDebtsService {
    static async getAllDetails() {
        console.log('Fetching worklog-debts/details')
        const response = await axios.get('http://localhost:8200/worklog-debts/details');
        console.log(`Received worklog-debts/details response, debts count: ${response.data.length}`);
        const debts = response.data as EmployeeDetailsWorklogDebts[];
        debts.forEach(elem => {
            const login = elem.employeeDetails.skypeLogin;
            if (!login) {
                elem.employeeDetails.skypeLogin = `anonymous-${elem.employeeDetails.id}`;
            }
        });
        return response;
    }

    static isLoginPresent(login: string) {
        return !login.startsWith('anonymous-');
    }
}
