import axios from "axios";
import {Employee} from "../models/employee/Employee";
import {Region} from "../models/region/Region";
import {EmployeeNotificationStatus} from "../models/worklogdebt/EmployeeNotificationStatus";

export default class EmployeeService {
    static async updateEmployee(employee: Employee) {
        console.log('PUT api/employees');
        await new Promise(resolve => setTimeout(resolve, 1000));
        return employee;
    }

    static async getAllEmployees() {
        console.log('GET api/employees')
        // const response = await axios.get('http://localhost:8080/api/employees');
        // console.log(`Received api/employees response, employees count: ${response.data.length}`);
        // return response;
        await new Promise(resolve => setTimeout(resolve, 1000))
        return [
            {
                id: 1,
                firstName: "Leonid",
                lastName: "Timofeev",
                jiraKey: "leonid_timofeev",
                skypeLogin: "live:.cid.a0112124d000i9sf9921",
                region: {
                    id: "b90d4673-09df-43aa-afc0-69e997c4c476",
                    name: "Georgia"
                } as Region,
                notificationStatus: {
                    notificationEnabled: true,
                    botConnected: true
                } as EmployeeNotificationStatus
            } as Employee,
            {
                id: 2,
                firstName: "Jason",
                lastName: "Timofeev",
                jiraKey: "jason_timofeev",
                skypeLogin: "skypelogin21023121",
                region: {
                    id: "b90d4673-09df-43aa-afc0-69e997c4c476",
                    name: "Georgia"
                } as Region,
                notificationStatus: {
                    notificationEnabled: false,
                    botConnected: false
                } as EmployeeNotificationStatus
            } as Employee,
            {
                id: 4,
                firstName: "Leonid",
                lastName: "Bond",
                jiraKey: "leonid_bond",
                skypeLogin: "skypelogin210231a",
                region: {
                    id: "b90d4673-09df-43aa-afc0-69e997c4c476",
                    name: "Georgia"
                } as Region,
                notificationStatus: {
                    notificationEnabled: false,
                    botConnected: true
                } as EmployeeNotificationStatus
            } as Employee,
        ]
    }
}