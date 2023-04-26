import axios from "axios";
import {Employee} from "../models/employee/Employee";
import {UpdateEmployeeData} from "../models/employee/UpdateEmployeeData";
import {CreateEmployeeData} from "../models/employee/CreateEmployeeData";

export default class EmployeeService {
    static async updateEmployee(updateEmployee: UpdateEmployeeData) {
        console.log('PUT api/employees');
        const response = await axios.put('http://localhost:8080/api/employees', updateEmployee);
        return response.data;
    }

    static async addEmployee(createEmployee: CreateEmployeeData) {
        console.log('POST api/employees');
        const response = await axios.post('http://localhost:8080/api/employees', createEmployee);
        return response.data;
    }

    static async deleteEmployee(employee: Employee) {
        console.log('DELETE api/employees');
        await axios.delete(`http://localhost:8080/api/employees/${employee.id}`);
    }

    static async getAllEmployees() {
        console.log('GET api/employees')
        const response = await axios.get('http://localhost:8080/api/employees');
        console.log(`Received api/employees response, employees count: ${response.data.length}`);
        return response.data;
    }
}
